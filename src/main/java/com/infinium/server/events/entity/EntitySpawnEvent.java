package com.infinium.server.events.entity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntitySpawnEvent {

    Event<EntitySpawnEvent> EVENT = EventFactory.createArrayBacked(EntitySpawnEvent.class, (listeners) -> (entity) -> {

        for (EntitySpawnEvent listener : listeners) {
            ActionResult result = listener.spawn(entity);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });



    ActionResult spawn(Entity entity);
    
}
