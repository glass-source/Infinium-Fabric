package com.infinium.server;

import com.infinium.server.commands.InfiniumCommand;
import com.infinium.server.commands.StaffCommand;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulspider.GhoulSpiderEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulzombie.GhoulZombieEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.berserker.BerserkerEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.raider.RaiderEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.sorcerer.ExplosiveSorcererEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidenderman.VoidEndermanEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidghast.VoidGhastEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidspider.VoidSpiderEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidzombie.VoidZombieEntity;
import com.infinium.server.entities.mobs.neutral.PepFrogEntity;
import com.infinium.server.world.gen.InfiniumEntitySpawn;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;


public class InfiniumRegistries {

    public static void init() {
        registerCommands();
        registerVoidMobAttributes();
        registerGhoulMobAttributes();
        registerNeutralMobAttributes();
        registerRaidMobAttributes();
        new InfiniumEntitySpawn().addSpawnRestrictions();
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(StaffCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerGhoulMobAttributes(){
        setAttributes(InfiniumEntityType.GHOUL_SPIDER, GhoulSpiderEntity.createGhoulSpiderAttributes());
        setAttributes(InfiniumEntityType.GHOUL_ZOMBIE, GhoulZombieEntity.createGhoulZombieAttributes());
    }
    private static void registerVoidMobAttributes(){
        setAttributes(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.createVoidGhastAttributes());
        setAttributes(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntity.createVoidSpiderAttributes());
        setAttributes(InfiniumEntityType.VOID_ENDERMAN, VoidEndermanEntity.createVoidEndermanAttributes());
        setAttributes(InfiniumEntityType.VOID_ZOMBIE, VoidZombieEntity.createVoidZombieAttributes());
    }

    private static void registerNeutralMobAttributes() {
        setAttributes(InfiniumEntityType.PEP_FROG, PepFrogEntity.createPepAttributes());
    }

    private static void registerRaidMobAttributes() {
        setAttributes(InfiniumEntityType.EXPLOSIVE_SORCERER, ExplosiveSorcererEntity.createSorcererAttributes());
        setAttributes(InfiniumEntityType.BERSERKER, BerserkerEntity.createBerserkerAttributes());
        setAttributes(InfiniumEntityType.RAIDER, RaiderEntity.createRaiderAttributes());
    }

    private static void setAttributes(EntityType<? extends LivingEntity> type, DefaultAttributeContainer.Builder attributes){
        FabricDefaultAttributeRegistry.register(type, attributes);
    }


}
