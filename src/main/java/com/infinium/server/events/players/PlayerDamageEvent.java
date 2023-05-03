package com.infinium.server.events.players;

import com.infinium.server.events.entity.EntitySpawnEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

import java.util.UUID;

public interface PlayerDamageEvent {

    Event<PlayerDamageEvent> EVENT = EventFactory.createArrayBacked(PlayerDamageEvent.class, (listeners) -> (serverPlayerEntity) -> {

        for (PlayerDamageEvent listener : listeners) {
            ActionResult result = listener.onPlayerDamage(serverPlayerEntity);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onPlayerDamage(UUID entity);

}
