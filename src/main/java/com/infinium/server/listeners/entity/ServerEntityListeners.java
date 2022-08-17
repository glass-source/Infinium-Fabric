package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.api.events.entity.EntitySpawnEvent;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.api.world.Location;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.voidmobs.VoidGhastEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;


public class ServerEntityListeners {

    private final Infinium instance;

    public ServerEntityListeners(Infinium instance){
        this.instance = instance;
    }

    public void registerListeners() {
        entitySpawnCallback();
    }

    private void entitySpawnCallback(){
        EntitySpawnEvent.EVENT.register((entity, loc) -> {
            var day = DateUtils.getDay();
            if (!(entity.getWorld() instanceof ServerWorld world)) return ActionResult.FAIL;


            return ActionResult.PASS;
        });
    }


}
