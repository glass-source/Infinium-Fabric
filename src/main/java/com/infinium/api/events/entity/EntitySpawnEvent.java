package com.infinium.api.events.entity;

import com.infinium.api.world.Location;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntitySpawnEvent {

    Event<EntitySpawnEvent> EVENT = EventFactory.createArrayBacked(EntitySpawnEvent.class, (listeners) -> (entity, loc) -> {

        for (EntitySpawnEvent listener : listeners) {
            ActionResult result = listener.spawn(entity, loc);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });



    ActionResult spawn(Entity entity, Location loc);
    
}
