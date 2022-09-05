package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.server.events.players.ServerPlayerConnectionEvents;
import com.infinium.server.InfiniumServerManager;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
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
        var sanityManager = core.getSanityManager();
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            var audience = core.getAdventure().audience(PlayerLookup.all(core.getServer()));
            if (!sanityManager.totalPlayers.contains(player)) sanityManager.totalPlayers.add(player);

            if (core.getEclipseManager().isActive()) audience.showBossBar(core.getEclipseManager().getBossBar());

            if (sanityManager.getData(player).get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);

            if (sanityManager.getData(player).get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 100, sanityManager.TIME_COOLDOWN);

            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
            return ActionResult.PASS;
        });

    }

    private void playerDisconnectCallback(){
        var sanityManager = instance.getCore().getSanityManager();
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            sanityManager.totalPlayers.remove(player);
            return ActionResult.PASS;
        });
    }

}
