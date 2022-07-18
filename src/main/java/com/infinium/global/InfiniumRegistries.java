package com.infinium.global;

import com.infinium.global.entities.InfiniumEntityType;
import com.infinium.global.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.global.commands.InfiniumCommand;
import com.infinium.global.commands.StaffCommand;
import com.infinium.global.items.groups.InfiniumItems;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;

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
        CommandRegistrationCallback.EVENT.register(StaffCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerEntityAttributes(){
        FabricDefaultAttributeRegistry.register(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.setAttributes());
    }

}
