package com.infinium;

import com.infinium.api.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Infinium implements ModInitializer {

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
        ModItems.init();
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
    }
}
