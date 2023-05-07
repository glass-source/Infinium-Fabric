package com.infinium.server.world.gen;

import com.infinium.server.entities.InfiniumEntityType;
import net.fabricmc.fabric.mixin.object.builder.SpawnRestrictionAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.Heightmap;

public class InfiniumEntitySpawn {
    
    public void addSpawnRestrictions() {
        //There's a better way to do this; but im lazy.
        applySpawnRestrictions(InfiniumEntityType.GHOUL_ZOMBIE);
        applySpawnRestrictions(InfiniumEntityType.GHOUL_SPIDER);
        applySpawnRestrictions(InfiniumEntityType.VOID_SPIDER);
        applySpawnRestrictions(InfiniumEntityType.VOID_ENDERMAN);
        applySpawnRestrictions(InfiniumEntityType.VOID_GHAST);
        applySpawnRestrictions(InfiniumEntityType.VOID_ZOMBIE);
    }

    private <T extends MobEntity> void applySpawnRestrictions(EntityType<T> entityType) {
        SpawnRestrictionAccessor.callRegister(entityType, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MobEntity::canMobSpawn);
    }

}
