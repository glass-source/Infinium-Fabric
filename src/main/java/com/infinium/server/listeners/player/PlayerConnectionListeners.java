package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.client.InfiniumClientManager;
import com.infinium.server.events.players.ServerPlayerConnectionEvents;
import com.infinium.server.InfiniumServerManager;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class PlayerConnectionListeners {

    private final Infinium instance;
    private final InfiniumServerManager core;

    public PlayerConnectionListeners(Infinium instance){
        this.instance = instance;
        this.core = instance.getCore();
    }

    public void registerListener(){
        playerConnectCallback();
        playerDisconnectCallback();
    }

    private void playerConnectCallback(){
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            var sanityManager = core.getSanityManager();
            var player = handler.getPlayer();
            var audience = core.getAdventure().audience(PlayerLookup.all(core.getServer()));

            if (!sanityManager.totalPlayers.contains(player)) sanityManager.totalPlayers.add(player);
            if (core.getEclipseManager().isActive()) audience.showBossBar(core.getEclipseManager().getBossBar());
            if (sanityManager.getData(player).get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);
            if (sanityManager.getData(player).get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 100, sanityManager.TIME_COOLDOWN);

            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
        });
    }

    private void playerDisconnectCallback(){

        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            var player = handler.player;
            var sanityManager = instance.getCore().getSanityManager();

            sanityManager.totalPlayers.remove(player);
        });

    }

}
