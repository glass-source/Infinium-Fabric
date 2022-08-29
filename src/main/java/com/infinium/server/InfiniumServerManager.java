package com.infinium.server;

import com.infinium.Infinium;
import com.infinium.global.config.InfiniumConfig;
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
import com.infinium.server.sanity.SanityManager;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.server.MinecraftServer;

public class InfiniumServerManager {

    private final Infinium instance;
    private FabricServerAudiences adventure;
    private MinecraftServer server;
    private final SanityManager sanityManager;
    private final SolarEclipseManager eclipseManager;

    public InfiniumServerManager(Infinium instance) {
        this.instance = instance;
        this.eclipseManager = new SolarEclipseManager(this.instance);
        this.sanityManager = new SanityManager(this.instance);
    }
    public void initMod(){
        onServerStart();
        onServerStop();
        initConfig();
        initRegistries();
    }

    private void onServerStart(){
        ServerLifecycleEvents.SERVER_STARTED.register(server1 -> {
            this.server = server1;
            this.adventure = FabricServerAudiences.of(this.server);
            this.sanityManager.registerSanityTask();
            this.eclipseManager.load();
            this.initListeners();
        });
    }

    private void onServerStop(){
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            this.eclipseManager.disable();
        });
    }

    private void initConfig(){
        MidnightConfig.init(Infinium.MOD_ID, InfiniumConfig.class);
    }

    private void initRegistries(){
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumEntityType.init();
        InfiniumRegistries.init();
        InfiniumPackets.registerC2SPackets();
    }

    private void initListeners(){
        registerPlayerListeners();
        new EntitySpawnListeners(instance).registerListeners();
    }

    private void registerPlayerListeners(){
        new PlayerDeathListeners(instance).registerListener();
        new PlayerConnectionListeners(instance).registerListener();
        new PlayerGlobalListeners(instance).registerListener();
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

}