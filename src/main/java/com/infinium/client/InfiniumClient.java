package com.infinium.client;


import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {

    private InfiniumClientManager clManager;

    @Override
    public void onInitializeClient() {
        clManager = new InfiniumClientManager(this);
        clManager.onClientStart();
    }




}
