package com.infinium.server;

import com.infinium.Infinium;
import com.infinium.global.config.data.DataManager;
import com.infinium.global.utils.DateUtils;
import com.infinium.networking.InfiniumPackets;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.items.blocks.InfiniumBlocks;
import com.infinium.server.listeners.entity.EntitySpawnListeners;
import com.infinium.server.listeners.player.PlayerConnectionListeners;
import com.infinium.server.listeners.player.PlayerDeathListeners;
import com.infinium.server.listeners.player.PlayerGlobalListeners;
import com.infinium.server.listeners.world.ServerWorldListeners;
import com.infinium.server.sanity.SanityManager;
import com.infinium.server.world.biomes.InfiniumBiomes;
import com.infinium.server.world.dimensions.InfiniumDimensions;
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
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.kyrptonaught.customportalapi.util.SHOULDTP;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

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


            this.dateUtils = new DateUtils(this.instance);
            this.dataManager.restoreWorldData();

            var serverRules = this.server.getGameRules();
            serverRules.get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, this.server);
            serverRules.get(GameRules.KEEP_INVENTORY).set(true, this.server);
            this.initPortals(server1);
            this.initListeners();
            this.sanityManager.registerSanityTask();
            this.eclipseManager.load();

        });
    }
    private void onServerStop(){
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            this.eclipseManager.disable();
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
    }
    private void initPortals(MinecraftServer server) {
        CustomPortalBuilder.beginPortal()
                .frameBlock(InfiniumBlocks.VOID_STONE_ORE)
                .lightWithItem(InfiniumItems.MYSTERIOUS_KEY)
                .destDimID(Infinium.id("the_void"))
                .tintColor(0, 0, 0)
                .registerBeforeTPEvent(portalEvent(InfiniumDimensions.THE_VOID, server))
                .flatPortal()
                .registerPortal();

        CustomPortalBuilder.beginPortal()
                .frameBlock(InfiniumBlocks.NIGHTMARE_OBSIDIAN)
                .lightWithItem(InfiniumItems.MYSTERIOUS_KEY)
                .destDimID(Infinium.id("the_nightmare"))
                .tintColor(255, 0,0)
                .registerBeforeTPEvent(portalEvent(InfiniumDimensions.THE_NIGHTMARE, server))
                .registerPortal();

        CustomPortalBuilder.beginPortal()
                .frameBlock(Blocks.AMETHYST_BLOCK)
                .lightWithItem(InfiniumItems.MYSTERIOUS_KEY)
                .destDimID(World.END.getValue())
                .tintColor(128, 0, 128)
                .flatPortal()
                .registerBeforeTPEvent(portalEvent(World.END, server))
                .registerPortal();
    }
    private void initListeners() {
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
    private @NotNull Function<Entity, SHOULDTP> portalEvent(RegistryKey<World> worldToKey, MinecraftServer server) {
        return entity -> {
            var worldFromKey = entity.getWorld().getRegistryKey();
            var worldTo = server.getWorld(worldToKey);

            if (worldTo != null) {
                double highestY;
                if (entity instanceof ServerPlayerEntity player) {

                    if (worldFromKey.equals(worldToKey)) {
                        highestY = getHighestY(server.getOverworld(), 0, 0);
                        player.teleport(server.getOverworld(), 0, highestY, 0, entity.getYaw(), entity.getPitch());

                    } else {
                        highestY = getHighestY(worldTo, 0, 0);
                        player.teleport(worldTo, 0, highestY, 0, entity.getYaw(), entity.getPitch());
                    }
                }
            }

            return SHOULDTP.CANCEL_TP;
        };
    }

    public double getHighestY(World world, double x, double z) {
        double Y = world.getTopY();
        for (double i = Y; i > world.getBottomY(); i--) {
            var blockPos = new BlockPos(x, i, z);
            var item = world.getBlockState(blockPos).getBlock().asItem();
            if (!item.equals(Items.AIR)) {
                Y = i;
                break;
            }
        }
        return Y + 1;
    }



}