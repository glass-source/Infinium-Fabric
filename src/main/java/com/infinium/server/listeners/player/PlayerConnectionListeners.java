package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.networking.InfiniumPackets;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.items.custom.InfiniumItem;
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
        var entityName = player.getEntityName();
        var logger = Infinium.getInstance().LOGGER;
        logger.info("Amount of players found in whitelist: #{}", WhitelistedPlayers.values().length);
        logger.info("Verifying whitelist for {}...", entityName);
        for (WhitelistedPlayers playerName : WhitelistedPlayers.values()) {
            if (player.getUuid().equals(playerName.uuid) || entityName.toLowerCase().contains("player")) {
                banned = false;
                break;
            }
        }
        if (banned) {
            if (player instanceof ServerPlayerEntity sp) {
                var buffer = PacketByteBufs.create();
                buffer.writeInt(1);
                ServerPlayNetworking.send(sp, InfiniumPackets.APPLY_WHITELIST_ID, buffer);
                logger.info("Player {} tried to join, but they're banned internally!", entityName);
            }
        } else {
            logger.info("{} was found in whitelist, approved.", entityName);
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
        ShiruSS("4881b948-f979-4b46-9031-ceb5f02e15d5"),
        Blackstamp("994702e0-1a8b-459a-9d4e-ef9d06469d0d"),
        UNKMage("7c395ed3-192f-4422-81f6-346b5c24b884"),
        ElTiwizZ("6275ea44-511d-4b38-a3e4-8df8268f932a"),
        Gus_Gus19("3f831faf-9f00-490f-9b4f-90c558197e76"),
        Wickedyf("0a24848a-1247-49ff-83bc-d5604f5ed610"),
        toposqui("c5ab7be0-d0e0-4cd8-9b23-eed961e2104a"),
        Lucmus("66ac4b0b-4cff-4702-bac5-187b4aba1185"),
        TheBoyPanda("d3f57305-6ce6-46e1-b2e3-d83177a5d71e"),
        zapata2013("923f666a-395e-49e0-9451-065a09416d2f"),
        ___Eclypse___("4baea546-a92d-4ee6-b096-f3c5ead71ada"),
        Niita("fa7c0be7-c2fa-41e9-8157-e034132a40f0"),
        tkedd("2c51c312-f515-46b8-a008-fce0a733c195"),
        wHermes("4e083407-4bfc-45e9-94c9-ad2770303f8e"),
        Ruldes("024d0bc4-e443-47cc-8404-7e82a5bf0441"),
        Pepe3012("f0740065-7b6c-470e-b6e9-2a93da15dd89"),
        Angel__Junior("bdbd24cf-7044-431a-a3dd-360410c0ff3b"),
        Azurex("257cc308-e6ca-485a-8445-065de91bee1c"),
        hheavensnight("e16dfef6-10db-44cd-adf4-5e2f8096c9f1"),
        Darkvid("03c803e0-20ae-411f-85d7-43ec52766d0e"),
        DarthG_("072ee52f-661b-4940-8f25-c42ca521e4bc")
        ;
        private final UUID uuid;
        WhitelistedPlayers(String playerUuid) {
            this.uuid = UUID.fromString(playerUuid);
        }
    }


}
