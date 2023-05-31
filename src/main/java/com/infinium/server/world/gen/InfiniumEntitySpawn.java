package com.infinium.server.world.gen;

import com.infinium.server.entities.InfiniumEntityType;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

public class InfiniumEntitySpawn {
    
    public void addSpawnRestrictions() {
        InfiniumEntityType.getEntityTypes().forEach(this::applySpawnRestrictions);
        applyPassiveSpawnRestrictions();
    }
    private <T extends MobEntity> void applySpawnRestrictions(EntityType<T> entityType) {
        SpawnRestrictionAccessor.callRegister(entityType, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    }
    private void applyPassiveSpawnRestrictions() {
        SpawnRestrictionAccessor.callRegister(
                InfiniumEntityType.DUCK,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                AnimalEntity::isValidNaturalSpawn);

        BiomeModifications.addSpawn(
                BiomeSelectors.categories(Biome.Category.values()),
                SpawnGroup.CREATURE,
                InfiniumEntityType.DUCK, 25, 3, 8);
    }

}
