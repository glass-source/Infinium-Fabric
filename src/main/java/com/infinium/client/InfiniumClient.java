package com.infinium.client;


import com.infinium.api.utils.ModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {

    private static MinecraftClient client;
    private static FabricClientAudiences audience;
    private static boolean isPaused = false;

    @Override
    public void onInitializeClient() {
        ModelPredicateProvider.registerBowModels();
        ModelPredicateProvider.registerEntityRenderer();
        initAudience();
    }

    private void initAudience(){
        ClientLifecycleEvents.CLIENT_STARTED.register(client1 -> {
            client = client1;
            audience = FabricClientAudiences.of();
        });

        ClientTickEvents.END_CLIENT_TICK.register(client1 -> {
            if (client1.player != null) {
                isPaused = client1.isPaused();
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client1 -> {
            client = null;
            audience = null;
        });
    }

    public static boolean isPaused(){
        return isPaused;
    }

    public static FabricClientAudiences getAudience(){
        return audience;
    }

    public static MinecraftClient getClient(){
        return client;
    }

}
