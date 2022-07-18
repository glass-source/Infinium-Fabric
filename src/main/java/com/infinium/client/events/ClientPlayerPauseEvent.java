package com.infinium.client.events;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

public interface ClientPlayerPauseEvent {

    Event<ClientPlayerPauseEvent> EVENT = EventFactory.createArrayBacked(ClientPlayerPauseEvent.class, (listeners) -> (player) -> {

        for (ClientPlayerPauseEvent listener : listeners) {
            ActionResult result = listener.pause(player);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult pause(ClientPlayerEntity player);
}
