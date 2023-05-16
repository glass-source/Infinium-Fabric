package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.items.custom.InfiniumItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.network.ServerPlayerEntity;

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
            var player = handler.player;
            var sanityManager = instance.getCore().getSanityManager();
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();

            saveCooldowns(player);
            eclipseManager.getBossBar().removePlayer(player);
            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
            sanityManager.totalPlayers.remove(player);
            infPlayer.onQuit();
        });

    }

    private void playerConnectCallback(){
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            var player = handler.getPlayer();
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();

            initSanity(player);
            loadCooldowns(player);
            infPlayer.onJoin();

            if (eclipseManager.isActive()) {
                eclipseManager.getBossBar().setVisible(true);
                eclipseManager.getBossBar().addPlayer(player);
            }
        });
    }

    private void loadCooldowns(ServerPlayerEntity player) {
        var data = ((EntityDataSaver) player).getPersistentData();
        var cooldownManager = player.getItemCooldownManager();
        var inventory = player.getInventory();

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i) != null) {
                if (inventory.getStack(i).getItem() instanceof InfiniumItem item) {
                    var dataString = "infinium.cooldown." + item;
                    int cooldownTicks = data.getInt(dataString);
                    cooldownManager.set(inventory.getStack(i).getItem(), cooldownTicks);
                }
            }
        }


    }

    private void saveCooldowns(ServerPlayerEntity player) {
        var data = ((EntityDataSaver) player).getPersistentData();
        var inventory = player.getInventory();

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.getStack(i) != null) {
                if (inventory.getStack(i).getItem() instanceof InfiniumItem item) {
                    var endingTickString = "infinium.cooldown." + item + ".ending-tick";
                    int cooldownTicks = data.getInt(endingTickString) - player.getServer().getTicks();
                    data.putInt("infinium.cooldown." + item, cooldownTicks);
                }
            }
        }
    }

    private void initSanity(ServerPlayerEntity player) {
        var sanityManager = core.getSanityManager();
        var data = sanityManager.getData(player);

        if (data.get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);
        if (data.get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 600, sanityManager.TIME_COOLDOWN);
        if (data.get(sanityManager.POSITIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.POSITIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.NEGATIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.NEGATIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.SOUND_COOLDOWN) == null) sanityManager.set(player, 10, sanityManager.SOUND_COOLDOWN);

        if (!sanityManager.totalPlayers.contains(player)) sanityManager.totalPlayers.add(player);
        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
    }





}
