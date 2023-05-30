package com.infinium.server;

import com.infinium.server.commands.InfiniumCommand;
import com.infinium.server.commands.StaffCommand;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.hostile.bosses.SuperNovaEntity;
import com.infinium.server.entities.mobs.hostile.dungeon.pirate.BlackBeardEntity;
import com.infinium.server.entities.mobs.hostile.dungeon.pirate.PirateSkeletonEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulCreeperEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulSpiderEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulWitchEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulZombieEntity;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareBlazeEntity;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareBruteEntity;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareGhastEntity;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareHoglinEntity;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareSkeletonEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.BerserkerEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.RaiderEntity;
import com.infinium.server.entities.mobs.hostile.raidmobs.ExplosiveSorcererEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidCreeperEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidEndermanEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidGhastEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidSkeletonEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidSpiderEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidZombieEntity;
import com.infinium.server.entities.mobs.neutral.duck.DuckEntity;
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
        registerDungeonMobAttributes();
        registerNightmareAttributes();
        registerBossAttributes();
        new InfiniumEntitySpawn().addSpawnRestrictions();
    }

    private static void registerCommands(){
        CommandRegistrationCallback.EVENT.register(StaffCommand::register);
        CommandRegistrationCallback.EVENT.register(InfiniumCommand::register);
    }

    private static void registerBossAttributes() {
        setAttributes(InfiniumEntityType.SUPER_NOVA, SuperNovaEntity.createNovaAttributes());
    }
    private static void registerGhoulMobAttributes(){
        setAttributes(InfiniumEntityType.GHOUL_SPIDER, GhoulSpiderEntity.createGhoulSpiderAttributes());
        setAttributes(InfiniumEntityType.GHOUL_ZOMBIE, GhoulZombieEntity.createGhoulZombieAttributes());
        setAttributes(InfiniumEntityType.GHOUL_CREEPER, GhoulCreeperEntity.createGhoulCreeperAttributes());
        setAttributes(InfiniumEntityType.GHOUL_WITCH, GhoulWitchEntity.createGhoulWitchAttributes());
    }

    private static void registerNightmareAttributes() {
        setAttributes(InfiniumEntityType.NIGHTMARE_SKELETON, NightmareSkeletonEntity.createNightmareSkeletonAttributes());
        setAttributes(InfiniumEntityType.NIGHTMARE_GHAST, NightmareGhastEntity.createNightmareGhastAttributes());
        setAttributes(InfiniumEntityType.NIGHTMARE_BRUTE, NightmareBruteEntity.createNightmareBruteAttributes());
        setAttributes(InfiniumEntityType.NIGHTMARE_HOGLIN, NightmareHoglinEntity.createNightmareHoglinAttributes());
        setAttributes(InfiniumEntityType.NIGHTMARE_BLAZE, NightmareBlazeEntity.createNightmareBlazeAttributes());
    }
    private static void registerVoidMobAttributes(){
        setAttributes(InfiniumEntityType.VOID_GHAST, VoidGhastEntity.createVoidGhastAttributes());
        setAttributes(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntity.createVoidSpiderAttributes());
        setAttributes(InfiniumEntityType.VOID_ENDERMAN, VoidEndermanEntity.createVoidEndermanAttributes());
        setAttributes(InfiniumEntityType.VOID_ZOMBIE, VoidZombieEntity.createVoidZombieAttributes());
        setAttributes(InfiniumEntityType.VOID_CREEPER, VoidCreeperEntity.createVoidCreeperAttributes());
        setAttributes(InfiniumEntityType.VOID_SKELETON, VoidSkeletonEntity.createVoidSkeletonAttributes());
    }

    private static void registerNeutralMobAttributes() {
        setAttributes(InfiniumEntityType.DUCK, DuckEntity.createDuckAttributes());
    }

    private static void registerDungeonMobAttributes() {
        setAttributes(InfiniumEntityType.PIRATE_SKELETON, PirateSkeletonEntity.createPirateSkeletonAttributes());
        setAttributes(InfiniumEntityType.BLACK_BEARD, BlackBeardEntity.createBlackBeardAttributes());
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
