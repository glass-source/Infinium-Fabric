package com.infinium.server.listeners.entity;

import com.infinium.Infinium;
import com.infinium.server.events.entity.EntitySpawnEvent;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.entities.mobs.voidmobs.VoidSpiderEntity;
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

            switch (world.getRegistryKey().getValue().toString()) {
                case "minecraft:overworld" -> {
                    if (entity instanceof VoidSpiderEntity) ChatFormatter.broadcastMessage("world!");
                }

                case "minecraft:the_nether" -> {
                    if (entity instanceof VoidSpiderEntity) ChatFormatter.broadcastMessage("nether!");

                }

                case "minecraft:the_end" -> {
                    if (entity instanceof VoidSpiderEntity) ChatFormatter.broadcastMessage("end!");

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
