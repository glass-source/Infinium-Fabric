package com.infinium.api.listeners.entity;

import com.infinium.api.entity.EntityBuilder;
import com.infinium.api.events.entity.EntitySpawn;
import com.infinium.api.world.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.ActionResult;


public class EntityListeners {


    private static ActionResult onSpawn(Entity entity, Location loc) {
        if(entity.getType() == EntityType.CREEPER) {
            CreeperEntity creeper = (CreeperEntity) entity;
            if(loc.getY() < 30) EntityBuilder.create(creeper).setHealth(4.0F).setInvisible(true).setCharged(true).setName("&dBlack Hole");
        }


        return ActionResult.PASS;

    }

    public static void registerListeners() {
        EntitySpawn.EVENT.register(EntityListeners::onSpawn);
    }



}
