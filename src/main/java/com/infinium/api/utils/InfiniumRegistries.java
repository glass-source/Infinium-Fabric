package com.infinium.api.utils;

import com.infinium.api.commands.SolarEclipseCommand;
import com.infinium.api.items.global.InfiniumItems;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.server.Main;

public class InfiniumRegistries {

    public static void init() {
        registerFuels();
        registerCommands();
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(InfiniumItems.ENDER_WAND, 1200);
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(SolarEclipseCommand::register);
    }

}
