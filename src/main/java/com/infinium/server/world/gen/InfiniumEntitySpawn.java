package com.infinium.server.world.gen;

import com.infinium.server.entities.InfiniumEntityType;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

public class InfiniumEntitySpawn {
    
    public void addSpawnRestrictions() {
        InfiniumEntityType.getEntityTypes().forEach(this::applySpawnRestrictions);
    }
    private <T extends MobEntity>  void applySpawnRestrictions(EntityType<T> entityType) {
        SpawnRestrictionAccessor.callRegister(entityType, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    }

}
