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
        if (data.get(sanityManager.SOUND_POINTS) == null) sanityManager.set(player, 100, sanityManager.SOUND_POINTS);

        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
    }


    public void checkBannedPlayers(PlayerEntity player) {
        boolean banned = true;
        var logger = Infinium.getInstance().LOGGER;
        logger.info("Verifying whitelist...");
        for (WhitelistedPlayers playerName : WhitelistedPlayers.values()) {
            if (player.getUuid().equals(playerName.uuid) || player.getEntityName().toLowerCase().contains("player")) {
                banned = false;
                break;
            }
        }
        if (banned) {
            var client = MinecraftClient.getInstance();
            logger.info("You are not whitelisted in this mod.");
            client.stop();
        } else {
            logger.info("Approved");
        }
    }
    public enum WhitelistedPlayers {
        Asunderer("381ce4f8-774c-4842-98e1-027c9ae9e8c5"),
        zDropeadx("723fa819-d0cc-4bdd-8fd7-950808549da9"),
        ITzFel17("815e3676-ca97-4689-8ac5-08c814721eac"),
        SrLexan("ce4fcc17-98bc-48f1-a8f6-25c1dde61bbe"),
        LechugaMC("966b523a-a31c-4fd2-b65c-62296dbee593"),
        Evelynnyb("9b0ceb80-fff2-482e-9383-1397a3e2dd2f"),
        MeteorCry("80f920bb-9dae-4dfa-9255-e669ca642571"),
        OweWhat("e6bbeba0-ebb0-442d-9f71-9dd31d4477f7"),
        Litro6666("d5216e9e-3959-4466-aa67-66efaa583abd"),
        waltttt("8badd71d-8a42-4303-9f5e-d26750d252c0"),
        joseoscar1("b0e92b10-c155-4f82-8e11-3a577f80976d"),
        papitasgeo_("cdc0c2d5-6de5-4af4-8c11-6bfff2e4f505"),
        Ggc69("747f9acc-aba0-4583-9951-a6f87e724018"),
        ShotaBlack("dc9ee133-bce9-4e79-b6e2-2a94c2f7f316"),
        Gatin72("9c98caea-356a-41d0-acb2-54b69bb95eeb"),
        zLucas("603ead46-4d3e-4abf-92c6-41bac64ca883"),
        Empa_("ecdf4cc9-0487-4d6f-bf09-8497deaf8b33"),
        SasuMC("64593c10-bdc4-4f14-947c-52d4ad846048"),
        OmkSpar_("c10b3b4e-3749-4583-87e1-e78cc44284ac"),
        CarmenedOwO("1845f9d0-7d40-4de2-aac3-593a8b710124"),
        Fabo("87bc8c76-68de-416b-834b-33296b1e8679"),
        AleSuarez("cd73752c-58c5-4c23-af2a-19fa2bcda7d9"),
        ;
        private final UUID uuid;
        WhitelistedPlayers(String playerUuid) {
            this.uuid = UUID.fromString(playerUuid);
        }
    }


}
