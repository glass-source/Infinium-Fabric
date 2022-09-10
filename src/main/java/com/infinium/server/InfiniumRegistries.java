package com.infinium.server;

import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.ghoulmobs.ghoulspider.GhoulSpiderEntity;
import com.infinium.server.entities.mobs.ghoulmobs.ghoulzombie.GhoulZombieEntity;
import com.infinium.server.entities.mobs.voidmobs.voidghast.VoidGhastEntity;
import com.infinium.server.commands.InfiniumCommand;
import com.infinium.server.commands.StaffCommand;
import com.infinium.server.entities.mobs.voidmobs.voidenderman.VoidEndermanEntity;
import com.infinium.server.entities.mobs.voidmobs.voidspider.VoidSpiderEntity;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

public class InfiniumRegistries {

    public static void init() {
        registerCommands();
        registerEntityAttributes();
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(StaffCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerEntityAttributes(){
        setAttributes(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.createVoidGhastAttributes());
        setAttributes(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntity.createVoidSpiderAttributes());
        setAttributes(InfiniumEntityType.GHOUL_SPIDER, GhoulSpiderEntity.createGhoulSpiderAttributes());
        setAttributes(InfiniumEntityType.GHOUL_ZOMBIE, GhoulZombieEntity.createGhoulZombieAttributes());
        setAttributes(InfiniumEntityType.VOID_ENDERMAN, VoidEndermanEntity.createVoidEndermanAttributes());
    }

    private static void setAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder attributes){
        FabricDefaultAttributeRegistry.register(type, attributes);
    }


}
