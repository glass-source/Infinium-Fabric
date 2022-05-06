package com.infinium;

import com.infinium.api.blocks.InfiniumBlocks;
import com.infinium.api.effects.InfiniumEffects;
import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.global.entity.InfiniumEntityType;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.listeners.entity.EntityListeners;
import com.infinium.api.listeners.player.ServerPlayerListeners;
import com.infinium.api.utils.InfiniumRegistries;
import com.infinium.global.sanity.SanityManager;
import com.infinium.global.sounds.InfiniumSounds;
import lombok.Getter;
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



    public static ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static final String MOD_ID = "infinium";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static FabricServerAudiences adventure;
    public static MinecraftServer server;

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        initAdventure();
        initMod();
    }



    private void initAdventure(){
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            Infinium.server = server;
            adventure = FabricServerAudiences.of(server);
        });
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            SolarEclipseManager.end();
            adventure = null;
        });
    }

    private void initMod(){
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumRegistries.init();
        InfiniumEntityType.init();
        ServerPlayerListeners.registerListener();
        EntityListeners.registerListeners();
        SanityManager.initSanityTask();
    }
}
