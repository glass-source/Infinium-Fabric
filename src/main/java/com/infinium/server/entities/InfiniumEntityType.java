package com.infinium.server.entities;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulspider.GhoulSpiderEntity;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulzombie.GhoulZombieEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidghast.VoidGhastEntity;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidenderman.VoidEndermanEntity;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumEntityType {

    public static EntityType<MagmaTridentEntity> MAGMA_TRIDENT;
    public static EntityType<VoidGhastEntity> VOID_GHAST;
    public static EntityType<VoidSpiderEntity> VOID_SPIDER;
    public static EntityType<GhoulSpiderEntity> GHOUL_SPIDER;
    public static EntityType<GhoulZombieEntity> GHOUL_ZOMBIE;
    public static EntityType<VoidEndermanEntity> VOID_ENDERMAN;
    public static EntityType<VoidZombieEntity> VOID_ZOMBIE;

    public static EntityType<PepFrogEntity> PEP_FROG;

    public static void init() {
        registerVoidMobs();
        registerGhoulMobs();
        registerNeutralMobs();
        MAGMA_TRIDENT = register("magma_trident", createEntityType(MagmaTridentEntity::new));
    }

    private static void registerVoidMobs(){
        VOID_GHAST = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_ghast"),
                FabricEntityTypeBuilder
                        .create(SpawnGroup.MONSTER, VoidGhastEntity::new)
                        .dimensions(EntityDimensions.fixed(4.5f, 4.5f)).build());

        VOID_SPIDER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_spider"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidSpiderEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(2F, 1.35F)).build());

        VOID_ENDERMAN = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_enderman"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidEndermanEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(0.95F, 4.0F)).build());

        VOID_ZOMBIE = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_zombie"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidZombieEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(0.8F, 1.95F)).build());
    }

    private static void registerGhoulMobs(){
        GHOUL_SPIDER = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "ghoul_spider"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GhoulSpiderEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(1.5F, 1.07F)).build());

        GHOUL_ZOMBIE = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "ghoul_zombie"),
                FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, GhoulZombieEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(0.8F, 1.95F)).build());
    }

    private static void registerNeutralMobs() {
        PEP_FROG = Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "pep_frog"),
                FabricEntityTypeBuilder.create(SpawnGroup.AMBIENT, PepFrogEntity::new)
                        .dimensions(EntityDimensions
                                .fixed(0.25f, 0.55f)).build());
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID + ":" + s), bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build();
    }



}
