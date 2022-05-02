package com.infinium.api.utils;

import com.infinium.api.items.global.InfiniumItems;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

public class InfiniumRegistries {

    public static void init() {
        registerFuels();
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(InfiniumItems.ENDER_WAND, 1200);
    }

}
