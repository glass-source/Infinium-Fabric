package com.infinium.api.utils;

import com.infinium.api.entities.InfiniumEntityType;
import com.infinium.api.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.global.commands.InfiniumCommand;
import com.infinium.global.commands.SolarEclipseCommand;
import com.infinium.api.items.global.InfiniumItems;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.boss.WitherEntity;

public class InfiniumRegistries {

    public static void init() {
        registerFuels();
        registerCommands();
        registerEntityAttributes();
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(InfiniumItems.ENDER_WAND, 1200);
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(SolarEclipseCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerEntityAttributes(){
        FabricDefaultAttributeRegistry.register(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.setAttributes());
    }

}
