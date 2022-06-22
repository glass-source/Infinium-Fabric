package com.infinium.api.entities;

import com.infinium.Infinium;
import com.infinium.api.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.api.entities.projectiles.MagmaTridentEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumEntityType {

    public static EntityType<MagmaTridentEntity> MAGMA_TRIDENT;
    public static EntityType<VoidGhastEntity> VOID_GHAST = Registry.register(
            Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID, "void_ghast"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, VoidGhastEntity::new)
                    .dimensions(EntityDimensions.fixed(4.0f, 4.0f)).build());


    public static void init() {
        MAGMA_TRIDENT = register("magma_trident", createEntityType(MagmaTridentEntity::new));
    }

    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Infinium.MOD_ID + ":" + s), bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).build();
    }

}
