package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.utils.Animation;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.events.players.PlayerDamageEvent;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.sounds.InfiniumSounds;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import xyz.nucleoid.disguiselib.api.EntityDisguise;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.infinium.server.items.custom.misc.InfiniumTotemItem.MAGMA_TOTEM_HEALTHBOOST;

public class PlayerDeathListeners {

    private final Infinium instance;
    private final InfiniumServerManager core;
    public static final EntityAttributeModifier firstTotemDebuff = new EntityAttributeModifier(UUID.randomUUID(), "Totem Debuff", -4, EntityAttributeModifier.Operation.ADDITION);
    public static final EntityAttributeModifier secondTotemDebuff = new EntityAttributeModifier(UUID.randomUUID(), "Totem Debuff", -100, EntityAttributeModifier.Operation.ADDITION);
    public PlayerDeathListeners(Infinium instance){
        this.instance = instance;
        this.core = instance.getCore();
    }

    public void registerListeners(){
        playerDeathCallback();
        playerDamageCallback();
    }
    private void playerDeathCallback() {
        if (core.getServer() == null) return;
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            player.setHealth(player.getMaxHealth());
            if (playerHasTotem(player, damageSource)) onTotemUse(player);
            else onPlayerDeath(player);
            return false;
        });
    }

    private void playerDamageCallback() {
        PlayerDamageEvent.EVENT.register((playerUUID, damageSource) -> {
            var player = core.getServer().getPlayerManager().getPlayer(playerUUID);
            if (player == null) return ActionResult.PASS;
            if (!player.interactionManager.getGameMode().isSurvivalLike()) return ActionResult.PASS;

            var day = core.getDateUtils().getCurrentDay();
            switch (damageSource.name) {
                case "cactus" -> {
                    if (day >= 14) {
                        if (player.isBlocking()) return ActionResult.FAIL;
                        if (playerHasTotem(player, damageSource)) {
                            var vec = player.getRotationVector().multiply(-1);
                            player.setVelocity(vec.getX(), vec.getY() + 0.1f, vec.getZ());
                            onTotemUse(player);
                            return ActionResult.PASS;
                        } else {
                            player.kill();
                            return ActionResult.SUCCESS;
                        }
                    }
                }

                case "fireworks", "explosion.player", "explosion" -> {
                    if (day >= 14) {
                        var cooldownManager = player.getItemCooldownManager();
                        player.clearActiveItem();
                        cooldownManager.set(Items.SHIELD, 80);
                        cooldownManager.set(InfiniumItems.VOID_SHIELD, 20);
                        return ActionResult.SUCCESS;
                    }
                }

            }

            return ActionResult.PASS;
        });
    }

    private void onTotemUse(ServerPlayerEntity player) {
        var sanityManager = instance.getCore().getSanityManager();
        var data = ((EntityDataSaver) player).getPersistentData();
        var totemString = "infinium.totems";
        int totems = data.getInt(totemString);
        var message = "";
        ItemStack totemStack = null;

        Hand[] handValue = Hand.values();
        for (Hand hand : handValue) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING) || itemStack.isOf(InfiniumItems.VOID_TOTEM) || itemStack.isOf(InfiniumItems.MAGMA_TOTEM)) {
                totemStack = itemStack;
                break;
            }
        }

        if (totemStack == null) return;

        var totemItem = totemStack.getItem();
        var playerName = player.getEntityName();
        player.setHealth(1.0F);
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        player.world.sendEntityStatus(player, EntityStatuses.USE_TOTEM_OF_UNDYING);
        player.incrementStat(Stats.USED.getOrCreateStat(totemItem));
        totemStack.decrement(1);
        Criteria.USED_TOTEM.trigger(player, totemStack);


        if (totemItem.equals(InfiniumItems.VOID_TOTEM)) {
            player.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 15, 0));
            data.putInt(totemString, totems + 3);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un &b&lVoid Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 3)));

        } else if (totemItem.equals(InfiniumItems.MAGMA_TOTEM)) {
            var entityAttributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

            if (entityAttributeInstance == null) return;
            if (entityAttributeInstance.hasModifier(MAGMA_TOTEM_HEALTHBOOST)) {
                entityAttributeInstance.removeModifier(MAGMA_TOTEM_HEALTHBOOST);
            }

            data.putInt(totemString, totems + 1);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un \n&c&lMagma Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));

        } else {
            data.putInt(totemString, totems + 1);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un \n&6&lTótem de la Inmortalidad" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));
        }

        sanityManager.decrease(player, 40, sanityManager.SANITY);
        ChatFormatter.broadcastMessage(message);

        totemEffects(player);
    }
    private void totemEffects(ServerPlayerEntity user) {
        var data = ((EntityDataSaver) user).getPersistentData();
        int totems = data.getInt("infinium.totems");
        var attributeInstance = user.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);


        if (totems >= 25) {
            if (!attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.addPersistentModifier(firstTotemDebuff);
        }

        if (totems >= 30) {
            if (attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.removeModifier(firstTotemDebuff);
            if (!attributeInstance.hasModifier(secondTotemDebuff)) attributeInstance.addPersistentModifier(secondTotemDebuff);
        }
    }
    private void onPlayerDeath(ServerPlayerEntity playerDied) {
        if (playerDied.isSpectator()) return;
        var pos = playerDied.getBlockPos();
        ((EntityDisguise) playerDied).removeDisguise();
        ChatFormatter.broadcastMessage(ChatFormatter.formatWithPrefix("&6&l%player% &7ha sucumbido ante el\n&5&lVacío Infinito".replaceAll("%player%", playerDied.getEntityName())));
        Animation.initImageForAll();

        core.getTotalPlayers().forEach(player -> player.playSound(InfiniumSounds.PLAYER_DEATH, SoundCategory.AMBIENT, 10, 0.7f));
        instance.getExecutor().schedule( () -> core.getEclipseManager().start(new Random().nextDouble(0.24, 1.6)) , 13, TimeUnit.SECONDS);
        playerDied.changeGameMode(GameMode.SPECTATOR);
        if (pos.getY() < -64) playerDied.teleport(pos.getX(), -64, pos.getZ());
        generatePlayerTombstone(playerDied);
    }

    private void generatePlayerTombstone(ServerPlayerEntity player) {
        var world = player.getWorld().getRegistryKey().getValue().toString();
        switch (world) {
            case "infinium:the_void" -> core.loadSchem("TumbaVoid", player);
            case "infinium:the_nightmare" -> core.loadSchem("TumbaNightmare", player);
            case "minecraft:the_end" -> core.loadSchem("TumbaEnd", player);
            case "minecraft:nether" -> core.loadSchem("TumbaNether", player);
            default -> core.loadSchem("TumbaOverworld", player);
        }
    }
    private boolean playerHasTotem(PlayerEntity player, DamageSource damageSource) {
        if (damageSource.isOutOfWorld()) return false;

        for (ItemStack stack : player.getItemsHand()) {
            if (stack.isOf(Items.TOTEM_OF_UNDYING) || stack.isOf(InfiniumItems.MAGMA_TOTEM) || stack.isOf(InfiniumItems.VOID_TOTEM))
                return true;
        }
        return false;
    }
}