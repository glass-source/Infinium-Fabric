package com.infinium.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {
    private static InfiniumClientManager clManager;

    @Override
    public void onInitializeClient() {
        clManager = new InfiniumClientManager();
        clManager.onClientStart();
    }


}
