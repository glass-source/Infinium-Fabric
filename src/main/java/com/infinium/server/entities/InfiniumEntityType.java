package com.infinium.server.entities;

import com.infinium.Infinium;
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
import com.infinium.server.entities.projectiles.MagmaTridentEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class InfiniumEntityType {
    public static EntityType<MagmaTridentEntity> MAGMA_TRIDENT;
    public static EntityType<VoidGhastEntity> VOID_GHAST;
    public static EntityType<VoidSpiderEntity> VOID_SPIDER;
    public static EntityType<GhoulSpiderEntity> GHOUL_SPIDER;
    public static EntityType<GhoulZombieEntity> GHOUL_ZOMBIE;
    public static EntityType<VoidEndermanEntity> VOID_ENDERMAN;
    public static EntityType<VoidZombieEntity> VOID_ZOMBIE;
    public static EntityType<ExplosiveSorcererEntity> EXPLOSIVE_SORCERER;
    public static EntityType<BerserkerEntity> BERSERKER;
    public static EntityType<RaiderEntity> RAIDER;
    public static EntityType<PepFrogEntity> PEP_FROG;

    public static List<EntityType<? extends MobEntity>> getEntityTypes() {
        return List.of(
                VOID_GHAST,
                VOID_SPIDER,
                GHOUL_SPIDER,
                GHOUL_ZOMBIE,
                VOID_ENDERMAN,
                EXPLOSIVE_SORCERER,
                BERSERKER,
                RAIDER,
                PEP_FROG,
                VOID_ZOMBIE
        );
    }

    public static void init() {
        registerVoidMobs();
        registerGhoulMobs();
        registerNeutralMobs();
        registerRaidMobs();
        MAGMA_TRIDENT = register("magma_trident", createEntityType(MagmaTridentEntity::new, SpawnGroup.MISC, 0.5f, 0.5f));
    }

    private static void registerVoidMobs(){
        VOID_GHAST = register("void_ghast", createEntityType(VoidGhastEntity::new, SpawnGroup.MONSTER, 4.5f, 4.5f));
        VOID_SPIDER = register("void_spider", createEntityType(VoidSpiderEntity::new, SpawnGroup.MONSTER, 2.0f, 1.35f));
        VOID_ENDERMAN = register("void_enderman", createEntityType(VoidEndermanEntity::new, SpawnGroup.MONSTER, 0.95f, 4.0f));
        VOID_ZOMBIE = register("void_zombie", createEntityType(VoidZombieEntity::new, SpawnGroup.MONSTER, 0.8f, 1.95f));
    }

    private static void registerGhoulMobs(){
        GHOUL_SPIDER = register("ghoul_spider", createEntityType(GhoulSpiderEntity::new, SpawnGroup.MONSTER, 1.5f, 1.1f));
        GHOUL_ZOMBIE = register("ghoul_zombie", createEntityType(GhoulZombieEntity::new, SpawnGroup.MONSTER, 0.8f, 1.95f));

    }

    private static void registerNeutralMobs() {
        PEP_FROG = register("pep_frog", createEntityType(PepFrogEntity::new, SpawnGroup.AMBIENT, 1.5f, 1.5f));
    }

    private static void registerRaidMobs() {
        EXPLOSIVE_SORCERER = register("explosive_sorcerer", createEntityType(ExplosiveSorcererEntity::new, SpawnGroup.MONSTER, 0.75f, 1.85f));
        BERSERKER = register("berserker", createEntityType(BerserkerEntity::new, SpawnGroup.MONSTER, 0.75f, 1.85f));
        RAIDER = register("raider", createEntityType(RaiderEntity::new, SpawnGroup.MONSTER, 0.75f, 1.85f));
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID + ":" + s), bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.changing(width, height)).build();
    }



}
