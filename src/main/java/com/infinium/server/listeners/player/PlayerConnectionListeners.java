package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.networking.InfiniumPackets;
import com.infinium.server.InfiniumServerManager;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class PlayerConnectionListeners {
    private final Infinium instance;
    private final InfiniumServerManager core;
    public PlayerConnectionListeners(Infinium instance){
        this.instance = instance;
        this.core = instance.getCore();
    }
    public void registerListeners(){
        playerConnectCallback();
        playerDisconnectCallback();
    }
    private void playerDisconnectCallback() {
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            if (server == null) return;
            var player = handler.player;
            var sanityManager = instance.getCore().getSanityManager();
            var eclipseManager = core.getEclipseManager();

            eclipseManager.getBossBar().removePlayer(player);
            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
        });
    }
    private void playerConnectCallback(){
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (server == null) return;
            var player = handler.getPlayer();
            var eclipseManager = core.getEclipseManager();
            player.getInventory().onOpen(player);
            initSanity(player);

            if (eclipseManager.isActive()) {
                eclipseManager.getBossBar().setVisible(true);
                eclipseManager.getBossBar().addPlayer(player);
            }
        });
    }
    private void initSanity(ServerPlayerEntity player) {
        var sanityManager = core.getSanityManager();
        var data = sanityManager.getData(player);

        if (data.get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);
        if (data.get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 300, sanityManager.TIME_COOLDOWN);
        if (data.get(sanityManager.POSITIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.POSITIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.NEGATIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 10, sanityManager.NEGATIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.FULL_HUNGER_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.FULL_HUNGER_COOLDOWN);
        if (data.get(sanityManager.EMPTY_HUNGER_COOLDOWN) == null) sanityManager.set(player, 10, sanityManager.EMPTY_HUNGER_COOLDOWN);
        if (data.get(sanityManager.SOUND_COOLDOWN) == null) sanityManager.set(player, 300, sanityManager.SOUND_COOLDOWN);

        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
    }
}
