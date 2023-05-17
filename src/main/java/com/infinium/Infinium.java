package com.infinium;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.infinium.global.config.data.adapters.ScheduledFutureInstanceCreator;
import com.infinium.global.utils.DateUtils;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.blocks.InfiniumBlocks;
import com.infinium.server.items.InfiniumItems;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.kyrptonaught.customportalapi.api.CustomPortalBuilder;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Infinium implements ModInitializer {
    private InfiniumServerManager core;

    private static Infinium instance;
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public final Logger LOGGER = LogUtils.getLogger();
    public static Identifier id(String arg) {
        return new Identifier(MOD_ID, arg);
    }

    public static final String MOD_ID = "infinium";

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(ScheduledFuture.class, new ScheduledFutureInstanceCreator())
            .setPrettyPrinting()
            .serializeNulls()
            .create();

    @Override
    public void onInitialize() {
        instance = this;
        this.core = new InfiniumServerManager(this);
        this.core.initMod();
        initPortals();
    }

    private void initPortals() {
        CustomPortalBuilder.beginPortal()
                .frameBlock(InfiniumBlocks.VOID_STONE)
                .lightWithItem(InfiniumItems.VOID_EYE)
                .destDimID(id("the_void"))
                .tintColor(0, 0, 0)
                .flatPortal()
                .registerPortal();
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

    public static Gson getGson() {
        return gson;
    }

    public DateUtils getDateUtils() {
        return this.core.getDateUtils();
    }

}
