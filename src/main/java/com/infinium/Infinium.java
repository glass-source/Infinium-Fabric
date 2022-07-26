package com.infinium;

import com.infinium.global.InfiniumRegistries;
import com.infinium.global.blocks.InfiniumBlocks;
import com.infinium.api.config.CustomMidnightConfig;
import com.infinium.api.config.InfiniumConfig;
import com.infinium.global.effects.InfiniumEffects;
import com.infinium.global.entities.InfiniumEntityType;
import com.infinium.api.eclipse.SolarEclipse;
import com.infinium.global.items.groups.InfiniumItems;
import com.infinium.global.listeners.entity.EntityListeners;
import com.infinium.client.listeners.ClientPlayerListeners;
import com.infinium.global.listeners.player.ServerPlayerListeners;
import com.infinium.global.sanity.SanityManager;
import com.infinium.global.world.dimensions.InfiniumDimensions;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Infinium implements ModInitializer {

    private static final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static final String MOD_ID = "infinium";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private static FabricServerAudiences adventure;
    private static MinecraftServer server;
    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        CustomMidnightConfig.init(MOD_ID, InfiniumConfig.class);
        initAdventure();
        registerMod();
    }

    private void initAdventure(){
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Infinium.server = server;
            Infinium.adventure = FabricServerAudiences.of(server);
        });

        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            SolarEclipse.end();
            Infinium.adventure = null;
        });
    }

    private void registerMod(){
        initModGadgets();
        initListeners();
    }

    private void initModGadgets(){
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumRegistries.init();
        InfiniumEntityType.init();
        SanityManager.initSanityTask();
        InfiniumDimensions.register();
    }

    private void initListeners(){
        ServerPlayerListeners.registerListener();
        ClientPlayerListeners.registerListeners();
        EntityListeners.registerListeners();
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static FabricServerAudiences getAdventure(){
        return adventure;
    }

    public static ScheduledExecutorService getExecutor(){
        return executorService;
    }
}
