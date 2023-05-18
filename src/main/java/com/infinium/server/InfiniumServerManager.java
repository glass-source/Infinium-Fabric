package com.infinium.server;

import com.infinium.Infinium;
import com.infinium.global.config.data.DataManager;
import com.infinium.global.utils.DateUtils;
import com.infinium.networking.InfiniumPackets;
import com.infinium.server.blocks.InfiniumBlocks;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.listeners.entity.EntitySpawnListeners;
import com.infinium.server.listeners.player.PlayerConnectionListeners;
import com.infinium.server.listeners.player.PlayerDeathListeners;
import com.infinium.server.listeners.player.PlayerGlobalListeners;
import com.infinium.server.listeners.world.ServerWorldListeners;
import com.infinium.server.sanity.SanityManager;
import com.infinium.server.world.biomes.InfiniumBiomes;
import com.infinium.server.world.dimensions.InfiniumDimensions;
import com.infinium.server.world.structure.InfiniumStructures;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.fabric.FabricAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class InfiniumServerManager {
    private final Infinium instance;
    private FabricServerAudiences adventure;
    private MinecraftServer server;
    private final SanityManager sanityManager;
    private final SolarEclipseManager eclipseManager;
    private DataManager dataManager;
    private DateUtils dateUtils;
    public InfiniumServerManager(final Infinium instance) {
        this.instance = instance;
        this.eclipseManager = new SolarEclipseManager(this.instance);
        this.sanityManager = new SanityManager(this.instance);
    }
    public void initMod(){
        initRegistries();
        onServerStart();
        onServerStop();
    }
    private void onServerStart(){
        ServerLifecycleEvents.SERVER_STARTED.register(server1 -> {
            this.server = server1;
            this.adventure = FabricServerAudiences.of(this.server);

            try {
                this.dataManager = new DataManager(this.instance);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.initListeners();
            this.dateUtils = new DateUtils(this.instance);
            this.dataManager.restoreWorldData();
            this.dataManager.restorePlayerData();
            this.eclipseManager.load();

            var nightmareWorld = this.server.getWorld(InfiniumDimensions.THE_NIGHTMARE);
            var serverRules = this.server.getGameRules();
            serverRules.get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, this.server);
            nightmareWorld.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, this.server);
            nightmareWorld.setTimeOfDay(18000);

            if (dateUtils.getCurrentDay() >= 42 && eclipseManager.isActive()) {
                serverRules.get(GameRules.NATURAL_REGENERATION).set(false, this.server);
            }
            this.sanityManager.registerSanityTask();
        });
    }



    private void onServerStop(){
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            this.eclipseManager.disable();
            this.dataManager.savePlayerData();
            this.sanityManager.stopSanityTask();
        });
    }

    private void initRegistries(){
        InfiniumEffects.init();
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEntityType.init();
        InfiniumRegistries.init();
        InfiniumDimensions.init();
        InfiniumBiomes.init();
        InfiniumPackets.initC2SPackets();
        InfiniumStructures.registerStructureFeatures();
    }

    private void initListeners(){
        registerPlayerListeners();
        new EntitySpawnListeners(instance).registerListeners();
        new ServerWorldListeners(instance).registerListeners();
    }

    private void registerPlayerListeners(){
        new PlayerDeathListeners(instance).registerListeners();
        new PlayerConnectionListeners(instance).registerListeners();
        new PlayerGlobalListeners(instance).registerListeners();
    }

    public SanityManager getSanityManager(){
        return sanityManager;
    }

    public SolarEclipseManager getEclipseManager(){
        return eclipseManager;
    }

    public MinecraftServer getServer() {
        return server;
    }

    public FabricServerAudiences getAdventure(){
        return adventure;
    }

    public List<ServerPlayerEntity> getTotalPlayers() {
        return this.server.getPlayerManager().getPlayerList();
    }

    public DataManager getDataManager() {
        return this.dataManager;
    }

    public DateUtils getDateUtils() {
        return this.dateUtils;
    }

    public void loadSchem(String filename, PlayerEntity player) {
        var world = player.getWorld();
        var X = player.getX();
        var Y = player.getY();
        var Z = player.getZ();
        loadSchem(filename, world, X, Y, Z);
    }

    public void loadSchem(String filename, World world, double X, double Y, double Z) {
        File file = new File(Infinium.getInstance().getCore().getServer().getFile("world").getAbsolutePath() + "/schematics/" + filename + ".schem");
        com.sk89q.worldedit.world.World adaptedWorld = FabricAdapter.adapt(world);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try {
            assert format != null;
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();

                try (EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(adaptedWorld).maxBlocks(-1).build()) {
                    Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(X, Y, Z)).copyEntities(true)
                            .ignoreAirBlocks(true).build();
                    try {
                        Operations.complete(operation);
                        editSession.close();

                    } catch (WorldEditException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}