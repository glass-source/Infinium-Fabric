package com.infinium.global.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.effects.InfiniumEffects;
import com.infinium.api.eclipse.SolarEclipse;
import com.infinium.api.events.players.PlayerUseTotemEvent;
import com.infinium.api.events.players.ServerPlayerConnectionEvents;
import com.infinium.global.items.groups.InfiniumItems;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.global.sanity.SanityManager;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.world.GameMode;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class ServerPlayerListeners {

    public static void registerListener(){
        playerConnectCallback();
        playerDisconnectCallback();
        playerDeathCallback();
        playerTotemCallback();
        playerElytraCallback();
    }
    
    private static void playerTotemCallback(){
        PlayerUseTotemEvent.EVENT.register(((player, totem) -> {
            var totemItem = totem.getItem();
            var data = ((EntityDataSaver) player).getPersistentData();
            var message = "";
            int totems = data.getInt("infinium.totems");

            player.setHealth(1.0F);
            player.clearStatusEffects();
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 900, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 100, 1));
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 800, 0));
            player.world.sendEntityStatus(player, (byte) 35);

            if (totemItem.equals(InfiniumItems.VOID_TOTEM)) {
                player.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 6, 0));
                data.putInt("infinium.totems", totems + 3);
                message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + player.getEntityName() + " &8ha consumido un &b&lVoid Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 3)));

            } else if (totemItem.equals(InfiniumItems.MAGMA_TOTEM)) {
                data.putInt("infinium.totems", totems + 1);
                message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + player.getEntityName() + " &8ha consumido un \n&c&lMagma Tótem" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));

            } else {
                data.putInt("infinium.totems", totems + 1);
                message = ChatFormatter.formatWithPrefix("&8El jugador &5&l" + player.getEntityName() + " &8ha consumido un \n&6&lTótem de la Inmortalidad" + " &8(Tótem #%.%)".replaceAll("%.%", String.valueOf(totems + 1)));
            }

            SanityManager.removeSanity((ServerPlayerEntity) player, 40);
            ChatFormatter.broadcastMessage(message);
            return ActionResult.PASS;
        }));
    }
    
    private static void playerDeathCallback(){

        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            if (Infinium.getServer() == null) return false;
            if (playerHasTotem(player, damageSource)) return false;
            return onPlayerDeath(player);
        });
    }

    private static void playerElytraCallback(){
       /*
        EntityElytraEvents.ALLOW.register(entity -> true);

        EntityElytraEvents.CUSTOM.register(((entity, tickElytra) -> {
            if (!(entity instanceof ServerPlayerEntity sp)) return false;
            var inv = sp.getInventory();
            var chestplate = inv.getArmorStack(2).getItem();
            return chestplate.equals(InfiniumItems.MAGMA_WINGS) || chestplate.equals(InfiniumItems.VOID_WINGS);
        }));
        */
    }

    private static void playerConnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            var data = ((EntityDataSaver) player).getPersistentData();
            var audience = Infinium.getAdventure().audience(PlayerLookup.all(Infinium.getServer()));

            if (!SanityManager.totalPlayers.contains(player)) SanityManager.totalPlayers.add(player);

            if (SolarEclipse.isActive()) {
                audience.showBossBar(SolarEclipse.getBossBar());
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

    private static void playerDisconnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            SanityManager.totalPlayers.remove(player);
            return ActionResult.PASS;
        });
    }

    private static boolean onPlayerDeath(ServerPlayerEntity playerDied){
        var pos = playerDied.getBlockPos();
        var audience = Infinium.getAdventure().audience(PlayerLookup.all(Infinium.getServer()));
        var times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(6), Duration.ofSeconds(3));
        var title = Title.title(Component.text(ChatFormatter.format("&6&k&l? &5Infinium &6&k&l?")), Component.text(""), times);
        if (pos.getY() < -64) playerDied.teleport(pos.getX(), -64, pos.getZ());
        if (playerDied.isSpectator()) {
            playerDied.setHealth(playerDied.getMaxHealth());
            return false;
        }
        Infinium.getExecutor().schedule(SolarEclipse::startFromDeath, 13, TimeUnit.SECONDS);
        ChatFormatter.broadcastMessage(ChatFormatter.formatWithPrefix("&7El jugador &6&l%player% &7sucumbio ante el\n&5&lVacío Infinito".replaceAll("%player%", playerDied.getEntityName())));
        playerDied.setHealth(20.0f);
        playerDied.changeGameMode(GameMode.SPECTATOR);
        audience.showTitle(title);
        audience.playSound(Sound.sound(Key.key("infinium:player_death"), Sound.Source.PLAYER, 10, 0.7f));

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