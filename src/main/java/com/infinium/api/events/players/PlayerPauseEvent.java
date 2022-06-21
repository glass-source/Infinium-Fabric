package com.infinium.api.events.players;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.ActionResult;

public interface PlayerPauseEvent {

    Event<PlayerPauseEvent> EVENT = EventFactory.createArrayBacked(PlayerPauseEvent.class, (listeners) -> (player) -> {

        for (PlayerPauseEvent listener : listeners) {
            ActionResult result = listener.pause(player);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult pause(ClientPlayerEntity player);
}
