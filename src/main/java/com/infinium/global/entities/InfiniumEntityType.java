package com.infinium.global.entities;

import com.infinium.Infinium;
import com.infinium.global.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.global.entities.mobs.voidmobs.VoidSpiderEntity;
import com.infinium.global.entities.projectiles.MagmaTridentEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumEntityType {

    public static EntityType<MagmaTridentEntity> MAGMA_TRIDENT = register("magma_trident", createEntityType(MagmaTridentEntity::new));
    public static EntityType<VoidGhastEntity> VOID_GHAST = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_ghast"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidGhastEntity::new)
                    .dimensions(EntityDimensions.fixed(4.5f, 4.5f)).build());

    public static EntityType<VoidSpiderEntity> VOID_SPIDER = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidSpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4F, 1.0F)).build());


    public static void init() {
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID + ":" + s), bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build();
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityFactory<T> factory, SpawnGroup group, float width, float height) {
        return FabricEntityTypeBuilder.create(group, factory).dimensions(EntityDimensions.changing(width, height)).build();
    }


}
