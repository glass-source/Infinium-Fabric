package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.server.InfiniumServerManager;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

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
            var player = handler.getPlayer();
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();
            if (eclipseManager.isActive()) {
                eclipseManager.getBossBar().setVisible(true);
                eclipseManager.getBossBar().addPlayer(player);
            }
            initSanity(player);
            infPlayer.onJoin();
        });
    }

    private void initSanity(ServerPlayerEntity player) {
        var sanityManager = core.getSanityManager();
        if (sanityManager.getData(player).get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);
        if (sanityManager.getData(player).get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 100, sanityManager.TIME_COOLDOWN);
        if (!sanityManager.totalPlayers.contains(player)) sanityManager.totalPlayers.add(player);
        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
    }

    private void playerDisconnectCallback(){
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            var player = handler.player;
            var sanityManager = instance.getCore().getSanityManager();
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();

            eclipseManager.getBossBar().removePlayer(player);
            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
            sanityManager.totalPlayers.remove(player);
            infPlayer.onQuit();
        });

    }

}
