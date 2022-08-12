package com.infinium.server;

import com.infinium.Infinium;
import com.infinium.api.config.CustomMidnightConfig;
import com.infinium.api.config.InfiniumConfig;
import com.infinium.api.eclipse.SolarEclipseManager;
import com.infinium.server.blocks.InfiniumBlocks;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.groups.InfiniumItems;
import com.infinium.server.listeners.entity.ServerEntityListeners;
import com.infinium.server.listeners.player.ServerPlayerListeners;
import com.infinium.server.sanity.SanityManager;
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
    }

    private void onServerStart(){
        ServerLifecycleEvents.SERVER_STARTED.register(server1 -> {
            this.server = server1;
            this.adventure = FabricServerAudiences.of(this.server);
            this.sanityManager.initSanityTask();
            initListeners();
        });

        initConfig();
        initRegistries();
    }

    private void onServerStop(){
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            this.eclipseManager.disable();
            this.adventure = null;
        });
    }

    private void initConfig(){
        CustomMidnightConfig.init(Infinium.MOD_ID, InfiniumConfig.class);
    }

    private void initRegistries(){
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumEntityType.init();
        InfiniumRegistries.init();
    }

    private void initListeners(){
        new ServerPlayerListeners(instance).registerListener();
        new ServerEntityListeners(instance).registerListeners();
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