package com.infinium.client;


import com.infinium.client.renderer.ModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {

    private static MinecraftClient client;
    private static FabricClientAudiences audience;
    private static ClientPlayerEntity clientPlayer;
    private static boolean isPaused = false;

    @Override
    public void onInitializeClient() {
        ModelPredicateProvider.initItemModels();
        ModelPredicateProvider.registerEntityRenderer();
        ModelPredicateProvider.registerEntityModelLayers();
        initAudience();
    }

    private void initAudience(){
        ClientLifecycleEvents.CLIENT_STARTED.register(cl -> {
            client = cl;
            audience = FabricClientAudiences.of();
            clientPlayer = client.player;
        });

        ClientTickEvents.END_CLIENT_TICK.register(cl -> {
            if (cl.player != null) {
                isPaused = cl.isPaused();
            }
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(cl -> {
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

    public static ClientPlayerEntity getPlayer(){
        return clientPlayer;
    }
}
