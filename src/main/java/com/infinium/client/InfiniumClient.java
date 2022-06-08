package com.infinium.client;


import com.infinium.api.utils.ModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {

    public static MinecraftClient client;
    public static FabricClientAudiences audience;


    @Override
    public void onInitializeClient() {
        ModelPredicateProvider.registerModels();
        initAudience();
    }

    private void initAudience(){
        ClientLifecycleEvents.CLIENT_STARTED.register(client1 -> {
            client = client1;
            audience = FabricClientAudiences.of();
        });

        ClientLifecycleEvents.CLIENT_STOPPING.register(client1 -> {
            client = null;
            audience = null;
        });
    }

}
