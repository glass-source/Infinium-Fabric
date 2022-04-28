package com.infinium.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;

@Environment(EnvType.CLIENT)

public class InfiniumClient implements ClientModInitializer {
    private final Audience client = FabricClientAudiences.of().audience();
    @Override
    public void onInitializeClient() {

    }
}
