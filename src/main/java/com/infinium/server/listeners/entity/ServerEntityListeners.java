package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.api.events.entity.EntitySpawn;
import com.infinium.global.utils.DateUtils;
import com.infinium.api.world.Location;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.ActionResult;


public class ServerEntityListeners {

    private final Infinium instance;

    public ServerEntityListeners(Infinium instance){
        this.instance = instance;
    }

    public void registerListeners() {
        EntitySpawn.EVENT.register(this::onCreatureSpawn);
    }

    private ActionResult onCreatureSpawn(Entity entity, Location loc) {
        int day = DateUtils.getDay();
        var world = loc.getWorld();
        return ActionResult.PASS;
    }

    private ActionResult onSpawn(Entity entity, Location loc) {
        if(entity.getType() == EntityType.CREEPER) {
            CreeperEntity creeper = (CreeperEntity) entity;
            if(loc.getY() < 30) {
                creeper.setFuseSpeed(4);
            }
        }

        return ActionResult.PASS;
    }



}
