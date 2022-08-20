package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.api.events.players.ServerPlayerConnectionEvents;
import com.infinium.global.utils.Animation;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.items.InfiniumItems;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

import java.util.Random;
import java.util.SplittableRandom;
import java.util.concurrent.TimeUnit;

public class ServerPlayerListeners {

    private final Infinium instance;
    private final InfiniumServerManager core;

    public ServerPlayerListeners(Infinium instance){
        this.instance = instance;
        this.core = instance.getCore();
    }

    public void registerListener(){
        playerConnectCallback();
        playerDisconnectCallback();
        playerDeathCallback();
        playerBedCallback();
    }

    private void playerConnectCallback(){
        var sanityManager = core.getSanityManager();
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            var data = ((EntityDataSaver) player).getPersistentData();
            var audience = core.getAdventure().audience(PlayerLookup.all(core.getServer()));
            if (!sanityManager.totalPlayers.contains(player)) sanityManager.totalPlayers.add(player);

            if (core.getEclipseManager().isActive()) {
                audience.showBossBar(core.getEclipseManager().getBossBar());
            }

            if (data.get("infinium.sanity") == null) {
                data.putInt("infinium.sanity", 100);
            }

            if (data.get("infinium.totems") == null) {
                data.putInt("infinium.totems", 0);
            }

            if (data.get("infinium.cooldown") == null) {
                data.putInt("infinium.cooldown", 0);
            }
            return ActionResult.PASS;
        });

    }

    private void playerBedCallback(){
        EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
            var day = DateUtils.getDay();
            if (day < 7) return;
            if (!(entity instanceof ServerPlayerEntity p)) return;
            var name = p.getEntityName();
            var random = new Random().nextInt(100);
            if (day < 35) {
                if (random < day) {
                    tpToWorld(World.NETHER, p, 2000);
                    ChatFormatter.broadcastMessageWithPrefix("&7El jugador &6&l" + name + " &7ha entrado a la &4&lNightmare &7" + "\nProbabilidad: " + random + " < " + day);
                } else {
                    p.sendMessage(ChatFormatter.textWithPrefix("&7Has Dormido. Probabilidad de ir a la &4&lNightmare&7: " + random + " > " + day), false);
                }
            } else {
                if (random < day / 2) {
                    tpToWorld(World.END, p, 5000);
                    ChatFormatter.broadcastMessageWithPrefix("&7El jugador &6&l" + name + " &7ha entrado a la &4&lNightmare &7" + "\nProbabilidad: " + random + " < " + day / 2);
                } else {
                    p.sendMessage(ChatFormatter.textWithPrefix("&7Has Dormido. Probabilidad de ir a la &4&lNightmare&7: " + random + " > " + day / 2), false);
                }
            }

        });
    }

    //TODO make The Void & Nightmare
    private void tpToWorld(RegistryKey<World> destination, ServerPlayerEntity who, int maxToTP) {
        if (who.getServer() == null) return;
        if (who.getServer().getWorld(destination) == null) return;

        var world = who.getWorld();
        var destinationWorld = world.getServer().getWorld(destination);
        SplittableRandom random = new SplittableRandom();
        int x = (random.nextBoolean() ? 1 : -1) * random.nextInt(maxToTP);
        int z = (random.nextBoolean() ? 1 : -1) * random.nextInt(maxToTP);
        who.wakeUp(false, true);
        who.teleport(destinationWorld, x, 101.05f, z, who.getYaw(), who.getPitch());
        who.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 30, 0));
        who.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * 30, 3));
        who.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 14, 0));
    }

    private void playerDisconnectCallback(){
        var sanityManager = instance.getCore().getSanityManager();
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            sanityManager.totalPlayers.remove(player);
            return ActionResult.PASS;
        });
    }

    private void playerDeathCallback(){
        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            if (core.getServer() == null) return false;
            if (playerHasTotem(player, damageSource)) {
                onTotemUse(player);
                return false;
            }
            return onPlayerDeath(player);
        });
    }

    private void onTotemUse(ServerPlayerEntity player){
        var sanityManager = instance.getCore().getSanityManager();
        ItemStack totemStack = null;
        var data = ((EntityDataSaver) player).getPersistentData();
        var message = "";
        int totems = data.getInt("infinium.totems");

        Hand[] handValue = Hand.values();
        for (Hand hand : handValue) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING) || itemStack.isOf(InfiniumItems.VOID_TOTEM) || itemStack.isOf(InfiniumItems.MAGMA_TOTEM)) {
                totemStack = itemStack;
                break;
            }
        }

        if (totemStack == null) return;

        String string = "hola";



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
            data.putInt("infinium.totems", totems + 3);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un &b&lVoid Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 3)));

        } else if (totemItem.equals(InfiniumItems.MAGMA_TOTEM)) {
            data.putInt("infinium.totems", totems + 1);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un \n&c&lMagma Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));

        } else {
            data.putInt("infinium.totems", totems + 1);
            message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + playerName + " &8ha consumido un \n&6&lTótem de la Inmortalidad" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));
        }
        sanityManager.removeSanity(player, 40);
        ChatFormatter.broadcastMessage(message);
    }

    private boolean onPlayerDeath(ServerPlayerEntity playerDied){
        var pos = playerDied.getBlockPos();
        var audience = core.getAdventure(). audience(PlayerLookup.all(core.getServer()));

        if (pos.getY() < -64) playerDied.teleport(pos.getX(), -64, pos.getZ());
        if (playerDied.isSpectator()) {
            playerDied.setHealth(playerDied.getMaxHealth());
            return false;
        }

        instance.getExecutor().schedule(core.getEclipseManager()::startFromDeath, 13, TimeUnit.SECONDS);
        ChatFormatter.broadcastMessage(ChatFormatter.formatWithPrefix("&7El jugador &6&l%player% &7sucumbio ante el\n&5&lVacío Infinito".replaceAll("%player%", playerDied.getEntityName())));
        playerDied.setHealth(20.0f);
        playerDied.changeGameMode(GameMode.SPECTATOR);
        audience.playSound(Sound.sound(Key.key("infinium:player_death"), Sound.Source.PLAYER, 10, 0.7f));
        Animation.initImageForAll();

        return true;
    }

    private static boolean playerHasTotem(PlayerEntity player, DamageSource damageSource) {
        if (damageSource.isOutOfWorld()) return false;

        for(ItemStack stack : player.getItemsHand()) {
            if (stack.isOf(Items.TOTEM_OF_UNDYING) || stack.isOf(InfiniumItems.MAGMA_TOTEM) || stack.isOf(InfiniumItems.VOID_TOTEM)) return true;
        }
        return false;
    }
}