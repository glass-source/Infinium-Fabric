package com.infinium;

import com.infinium.server.InfiniumServerManager;
import net.fabricmc.api.ModInitializer;
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
    private static InfiniumServerManager core;
    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        core = new InfiniumServerManager(this);
        core.initMod();
    }

    public static MinecraftServer getServer() {
        return core.getServer();
    }

    public static FabricServerAudiences getAdventure(){
        return core.getAdventure();
    }

    public InfiniumServerManager getCore(){
        return core;
    }

    public Infinium getInstance(){
        return this;
    }

    public static ScheduledExecutorService getExecutor(){
        return executorService;
    }
}
