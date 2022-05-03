package com.infinium;

import com.infinium.api.blocks.InfiniumBlocks;
import com.infinium.api.effects.InfiniumEffects;
import com.infinium.api.entity.types.InfiniumEntityTypes;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.utils.InfiniumRegistries;
import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class Infinium implements ModInitializer {



    public static @Getter ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    public static String MOD_ID = "infinium";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    private FabricServerAudiences adventure;

    public FabricServerAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure without a running server!");
        }
        return adventure;
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        InfiniumItems.init();
        InfiniumBlocks.init();
        InfiniumEffects.init();
        InfiniumRegistries.init();
        InfiniumEntityTypes.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
    }
}
