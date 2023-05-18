package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.items.custom.InfiniumItem;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
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
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();

            eclipseManager.getBossBar().removePlayer(player);
            sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
            infPlayer.onQuit();
        });

    }
    private void playerConnectCallback(){
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (server == null) return;
            var player = handler.getPlayer();
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(player);
            var eclipseManager = core.getEclipseManager();

            initCooldowns(player);
            initSanity(player);
            checkBannedPlayers(player);
            infPlayer.onJoin();

            if (eclipseManager.isActive()) {
                eclipseManager.getBossBar().setVisible(true);
                eclipseManager.getBossBar().addPlayer(player);
            }
        });
    }

    private void initCooldowns(ServerPlayerEntity user) {
        var data = ((EntityDataSaver) user).getPersistentData();
        var cooldownString = "infinium.cooldown." + this;

        if (data.get(cooldownString) == null) {

            var inventory = user.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i) != null) {
                    if (inventory.getStack(i).getItem() instanceof InfiniumItem item) {
                        item.setCooldown(user, inventory.getStack(i).getItem(), 0);
                    }
                }
            }
        }
    }

    private void initSanity(ServerPlayerEntity player) {
        var sanityManager = core.getSanityManager();
        var data = sanityManager.getData(player);

        if (data.get(sanityManager.SANITY) == null) sanityManager.set(player, 100, sanityManager.SANITY);
        if (data.get(sanityManager.TIME_COOLDOWN) == null) sanityManager.set(player, 300, sanityManager.TIME_COOLDOWN);
        if (data.get(sanityManager.POSITIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.POSITIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.NEGATIVE_HEALTH_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.NEGATIVE_HEALTH_COOLDOWN);
        if (data.get(sanityManager.FULL_HUNGER_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.FULL_HUNGER_COOLDOWN);
        if (data.get(sanityManager.EMPTY_HUNGER_COOLDOWN) == null) sanityManager.set(player, 20, sanityManager.EMPTY_HUNGER_COOLDOWN);
        if (data.get(sanityManager.SOUND_COOLDOWN) == null) sanityManager.set(player, 240, sanityManager.SOUND_COOLDOWN);
        if (data.get(sanityManager.SOUND_POINTS) == null) sanityManager.set(player, 0, sanityManager.SOUND_POINTS);

        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
    }


    public void checkBannedPlayers(PlayerEntity player) {
        var logger = Infinium.getInstance().LOGGER;
        logger.info("Checking banned players...");
        for (BannedPlayers playerName : BannedPlayers.values()) {
            if (player.getUuid().equals(playerName.uuid) || player.getEntityName().equals(playerName.toString())) {
                var client = MinecraftClient.getInstance();
                logger.info("You are banned from using this mod! \nClosing the game now.");
                client.execute(() -> client.getWindow().close());
                break;
            }
        }
    }
    public enum BannedPlayers {
        DREAM("ec70bcaf-702f-4bb8-b48d-276fa52a780c"),
        ALEIV("f04891b6-3a22-49c3-a8c0-bdf7e415243a"),
        DjMaRiiO("b8351a40-f0dc-4996-adfd-101311b8fdd9"),
        TETUISMC("9c626690-39a8-4163-ae70-8643caa6009c"),
        Asunderer("");


        private final UUID uuid;
        BannedPlayers(UUID playerUuid){
            this.uuid = playerUuid;
        }
        BannedPlayers(String playerUuid) {
            this.uuid = UUID.fromString(playerUuid);
        }
    }


}
