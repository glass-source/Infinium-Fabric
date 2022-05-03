package com.infinium;

import com.infinium.api.blocks.InfiniumBlocks;
import com.infinium.api.effects.InfiniumEffects;
import com.infinium.global.entity.InfiniumEntityType;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.listeners.entity.EntityListeners;
import com.infinium.api.listeners.player.ServerPlayerListeners;
import com.infinium.api.utils.InfiniumRegistries;
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
    public static @Getter MinecraftServer server;

    public FabricServerAudiences adventure() {
        if(adventure == null) {

            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return adventure;
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        initMod();
        initAdventure();
        registerServer();
    }

    private void initAdventure(){
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
    }

    private void initMod(){
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumRegistries.init();
        InfiniumEntityType.init();
        ServerPlayerListeners.registerListener();
        EntityListeners.registerListeners();
    }

    private void registerServer(){
        ServerLifecycleEvents.SERVER_STARTING.register(server1 -> server = server1);
    }
}
