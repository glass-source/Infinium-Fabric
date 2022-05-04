package com.infinium.global.entity;

import com.infinium.Infinium;
import com.infinium.global.entity.list.MagmaTridentEntity;
import com.infinium.global.entity.list.SuperNova;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.registry.Registry;

public class InfiniumEntityType {

    public static EntityType<MagmaTridentEntity> MAGMA_TRIDENT;
    public static EntityType<SuperNova> SUPERNOVA;


    public static void init() {
        MAGMA_TRIDENT = register("magma_trident", createEntityType(MagmaTridentEntity::new));
        SUPERNOVA = register("supernova", FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, SuperNova::new).fireImmune().specificSpawnBlocks(Blocks.WITHER_ROSE).dimensions(EntityDimensions.changing(0.9F, 3.5F)).trackRangeBlocks(10).build());


    }



    private static <T extends Entity> EntityType<T> register(String s, EntityType<T> bombEntityType) {
        return Registry.register(Registry.ENTITY_TYPE, Infinium.MOD_ID + ":" + s, bombEntityType);
    }

    private static <T extends Entity> EntityType<T> createEntityType(EntityType.EntityFactory<T> factory) {
        return FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory).dimensions(EntityDimensions.changing(0.5f, 0.5f)).trackRangeBlocks(4).trackedUpdateRate(20).build();
    }

}
