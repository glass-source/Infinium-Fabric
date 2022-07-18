package com.infinium.api.events.entity;

import com.infinium.api.world.Location;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public interface EntitySpawn {

    Event<EntitySpawn> EVENT = EventFactory.createArrayBacked(EntitySpawn.class, (listeners) -> (entity, loc) -> {

        for (EntitySpawn listener : listeners) {
            ActionResult result = listener.spawn(entity, loc);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });



    ActionResult spawn(Entity entity, Location loc);
    
}
