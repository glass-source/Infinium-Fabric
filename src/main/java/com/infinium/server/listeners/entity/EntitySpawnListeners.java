package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.global.utils.DateUtils;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.events.entity.EntitySpawnEvent;
import net.minecraft.block.AirBlock;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;


public class EntitySpawnListeners {

    private final InfiniumServerManager core;

    public EntitySpawnListeners(Infinium instance){
        this.core = instance.getCore();
    }

    public void registerListeners() {
        entitySpawnCallback();
    }

    private void entitySpawnCallback(){
        EntitySpawnEvent.EVENT.register((entity) -> {
            if (entity.getWorld().isClient) return ActionResult.FAIL;
            var day = DateUtils.getDay();
            var world = entity.getWorld();
            var blockPos = entity.getBlockPos();
            var block = world.getBlockState(blockPos.down()).getBlock();

            if (entity instanceof InfiniumEntity && (block instanceof FluidBlock || block instanceof AirBlock)) {
                entity.remove(Entity.RemovalReason.DISCARDED);
                return ActionResult.FAIL;
            }



            switch (world.getRegistryKey().getValue().toString()) {
                case "minecraft:overworld" -> {

                }

                case "minecraft:the_nether" -> {


                }

                case "minecraft:the_end" -> {


                }

                case "infinium:the_void" -> {

                }
                case "infinium:nightmare" -> {

                }

            }

            return ActionResult.PASS;
        });
    }


}
