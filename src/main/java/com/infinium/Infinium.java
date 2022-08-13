package com.infinium;

import com.infinium.server.InfiniumServerManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Infinium implements ModInitializer {

    private InfiniumServerManager core;
    private static Infinium instance;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public static final String MOD_ID = "infinium";

    public static Identifier id(String arg) {
        return new Identifier(MOD_ID, arg);
    }

    @Override
    public void onInitialize() {
        instance = this;
        core = new InfiniumServerManager(this);
        core.initMod();
    }

    public InfiniumServerManager getCore(){
        return core;
    }

    public ScheduledExecutorService getExecutor(){
        return executorService;
    }

    public static Infinium getInstance(){
        return instance;
    }
}
