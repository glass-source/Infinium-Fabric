package com.infinium.global;

import com.infinium.global.entities.InfiniumEntityType;
import com.infinium.global.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.global.commands.InfiniumCommand;
import com.infinium.global.commands.StaffCommand;
import com.infinium.global.entities.mobs.voidmobs.VoidSpiderEntity;
import com.infinium.global.items.groups.InfiniumItems;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

public class InfiniumRegistries {

    public static void init() {
        registerCommands();
        registerEntityAttributes();
    }

    private static void registerFuels() {
        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(InfiniumItems.MAGMA_TOTEM, 32000);
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(StaffCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerEntityAttributes(){
        setAttributes(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.createVoidGhastAttributes());
        setAttributes(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntity.createVoidSpiderAttributes());
    }

    private static void setAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder attributes){
        FabricDefaultAttributeRegistry.register(type, attributes);
    }


}
