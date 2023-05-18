package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.utils.Animation;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.events.players.PlayerDamageEvent;
import com.infinium.server.functions.HeadFunctions;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.sounds.InfiniumSounds;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.PlayerSkullBlock;
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
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.world.GameMode;
import xyz.nucleoid.disguiselib.api.EntityDisguise;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PlayerDeathListeners {
    private final Infinium instance;
    private final InfiniumServerManager core;
    public static final EntityAttributeModifier firstTotemDebuff = new EntityAttributeModifier(UUID.randomUUID(), "First Totem Debuff", -4, EntityAttributeModifier.Operation.ADDITION);
    public static final EntityAttributeModifier secondTotemDebuff = new EntityAttributeModifier(UUID.randomUUID(), "Second Totem Debuff", -8, EntityAttributeModifier.Operation.ADDITION);
    public static final EntityAttributeModifier finalTotemDebuff = new EntityAttributeModifier(UUID.randomUUID(), "Third Totem Debuff", -100, EntityAttributeModifier.Operation.ADDITION);
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
        PlayerDamageEvent.EVENT.register((playerUUID, damageSource, amount, isCancelled) -> {
            if (isCancelled) return ActionResult.PASS;

            var player = core.getServer().getPlayerManager().getPlayer(playerUUID);
            if (player == null) return ActionResult.PASS;
            if (!player.interactionManager.getGameMode().isSurvivalLike()) return ActionResult.PASS;

            var day = core.getDateUtils().getCurrentDay();
            switch (damageSource.name) {
                case "cactus" -> {
                    if (day >= 14) {
                        if (playerHasTotem(player, damageSource)) {
                            var vec = player.getRotationVector().multiply(-1);
                            player.setVelocity(vec.getX(), vec.getY() + 0.1f, vec.getZ());
                            onTotemUse(player);
                        } else {
                            player.kill();
                        }
                    }
                }

                case "explosion.player", "explosion" -> {
                    if (day >= 14) {
                        var cooldownManager = player.getItemCooldownManager();
                        cooldownManager.set(Items.SHIELD, 80);
                        cooldownManager.set(InfiniumItems.VOID_SHIELD, 20);
                        player.clearActiveItem();
                    }
                }

            }

            return ActionResult.PASS;
        });
    }

    private void onTotemUse(ServerPlayerEntity player) {
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
        totemEffects(player, totemStack);
    }
    private void totemEffects(ServerPlayerEntity player, ItemStack totemStack) {
        if (player.world.isClient) return;
        var attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attributeInstance == null) return;

        var data = ((EntityDataSaver) player).getPersistentData();
        int totems = data.getInt("infinium.totems");
        var totemString = "infinium.totems";
        var sanityManager = instance.getCore().getSanityManager();
        var totemItem = totemStack.getItem();
        var playerName = player.getEntityName();
        var message = "";

        player.setHealth(1.0F);
        player.clearStatusEffects();
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
        player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
        player.getWorld().sendEntityStatus(player, EntityStatuses.USE_TOTEM_OF_UNDYING);
        player.incrementStat(Stats.USED.getOrCreateStat(totemItem));
        totemStack.decrement(1);
        Criteria.USED_TOTEM.trigger(player, totemStack);

        if (totems >= 5) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20 * 20, 2));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 20 * 20, 2));
        }

        if (totems >= 10) {
            player.removeStatusEffect(StatusEffects.REGENERATION);
            player.removeStatusEffect(StatusEffects.ABSORPTION);
            player.removeStatusEffect(StatusEffects.FIRE_RESISTANCE);
        }

        if (totems >= 15) {
            if (!attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.addPersistentModifier(firstTotemDebuff);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 20 * 20, 4));
        }

        if (totems >= 20) {
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 6, 0));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * 4, 0));

        }

        if (totems >= 25) {
            if(!attributeInstance.hasModifier(secondTotemDebuff)) attributeInstance.addPersistentModifier(secondTotemDebuff);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 20 * 20, 4));
        }

        if (totems >= 30) {
            if(!attributeInstance.hasModifier(finalTotemDebuff)) attributeInstance.addPersistentModifier(finalTotemDebuff);
        }

        if (totemItem.equals(InfiniumItems.VOID_TOTEM)) {
            player.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 15, 0));
            data.putInt(totemString, totems + 3);
            message = ChatFormatter.formatWithPrefix("&5&l" + playerName + " &8ha consumido un &b&lVoid Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 3)));

        } else if (totemItem.equals(InfiniumItems.MAGMA_TOTEM)) {
            data.putInt(totemString, totems + 1);
            message = ChatFormatter.formatWithPrefix("&5&l" + playerName + " &8ha consumido un \n&c&lMagma Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));

        } else {
            data.putInt(totemString, totems + 1);
            message = ChatFormatter.formatWithPrefix("&5&l" + playerName + " &8ha consumido un \n&6&lTótem de la Inmortalidad" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));
        }

        sanityManager.decrease(player, 25, sanityManager.SANITY);
        ChatFormatter.broadcastMessage(message);
    }
    private void onPlayerDeath(ServerPlayerEntity playerDied) {
        if (playerDied.isSpectator()) return;
        var pos = playerDied.getBlockPos();
        var attributeInstance = playerDied.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        ((EntityDisguise) playerDied).removeDisguise();

        ChatFormatter.broadcastMessage(ChatFormatter.formatWithPrefix("&6&l%player% &7ha sucumbido ante el\n&5&lVacío Infinito".replaceAll("%player%", playerDied.getEntityName())));
        Animation.initImageForAll();

        core.getTotalPlayers().forEach(player -> player.playSound(InfiniumSounds.PLAYER_DEATH, SoundCategory.AMBIENT, 10, 0.7f));
        instance.getExecutor().schedule( () -> core.getEclipseManager().start(new Random().nextDouble(0.24, 1.6)) , 13, TimeUnit.SECONDS);
        playerDied.changeGameMode(GameMode.SPECTATOR);

        if (pos.getY() <= -64) playerDied.teleport(pos.getX(), -60, pos.getZ());
        generatePlayerTombstone(playerDied);

        if (attributeInstance == null) return;
        attributeInstance.removeModifier(finalTotemDebuff);
        attributeInstance.removeModifier(secondTotemDebuff);
        attributeInstance.removeModifier(firstTotemDebuff);
    }

    private void generatePlayerTombstone(ServerPlayerEntity player)  {
        player.teleport(player.getX(), player.getY() + 1, player.getZ());
        try {
            var world = player.getWorld().getRegistryKey().getValue().toString();
            switch (world) {
                case "infinium:the_void" -> core.loadSchem("TumbaVoidFinal", player);
                case "infinium:the_nightmare" -> core.loadSchem("TumbaNightmareFinal", player);
                case "minecraft:the_end" -> core.loadSchem("TumbaEndFinal", player);
                case "minecraft:the_nether" -> core.loadSchem("TumbaNetherFinal", player);
                default -> core.loadSchem("TumbaOverworldFinal", player);
            }

        } catch (Exception ex) {ex.printStackTrace();}

        var head = HeadFunctions.getPlayerHead(player.getEntityName(), 1);
        if (head == null) return;
        if (!head.hasNbt()) return;
        var world = player.getWorld();
        var block = Block.getBlockFromItem(head.getItem());
        var state = block.getDefaultState().rotate(BlockRotation.CLOCKWISE_180);
        world.setBlockState(player.getCameraBlockPos(), state);

        if (block instanceof PlayerSkullBlock playerSkullBlock) {
            playerSkullBlock.onPlaced(world, player.getCameraBlockPos(), playerSkullBlock.getDefaultState(), player, head);
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