package com.infinium.api.listeners.entity;

import com.infinium.api.entities.InfiniumEntityType;
import com.infinium.api.entities.mobs.voidmobs.VoidGhastEntity;
import com.infinium.api.events.entity.EntitySpawn;
import com.infinium.api.utils.Utils;
import com.infinium.api.world.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.ActionResult;


public class EntityListeners {


    public static void registerListeners() {
        EntitySpawn.EVENT.register(EntityListeners::onCreatureSpawn);
    }

    private static ActionResult onCreatureSpawn(Entity entity, Location loc) {
        int day = Utils.getDay();
        var world = loc.getWorld();

        if (entity.getType().equals(EntityType.GHAST)) {
            /*
            entity.remove(Entity.RemovalReason.DISCARDED);
            VoidGhastEntity ghastEntity = new VoidGhastEntity(InfiniumEntityType.VOID_GHAST, world);
            world.spawnEntity(ghastEntity);
             */
        }

        return ActionResult.PASS;
    }

    private static ActionResult onSpawn(Entity entity, Location loc) {
        if(entity.getType() == EntityType.CREEPER) {
            CreeperEntity creeper = (CreeperEntity) entity;
            if(loc.getY() < 30) {
                creeper.setFuseSpeed(4);
            }
        }

        return ActionResult.PASS;
    }



}
