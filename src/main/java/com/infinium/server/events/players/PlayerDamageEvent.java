package com.infinium.server.events.players;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.ActionResult;

import java.util.UUID;

public interface PlayerDamageEvent {

    Event<PlayerDamageEvent> EVENT = EventFactory.createArrayBacked(PlayerDamageEvent.class, (listeners) -> (serverPlayerEntity, damageSource, amount, isCancelled) -> {

        for (PlayerDamageEvent listener : listeners) {
            ActionResult result = listener.onPlayerDamage(serverPlayerEntity, damageSource, amount, isCancelled);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult onPlayerDamage(UUID entity, DamageSource source, double amount, boolean isCancelled);

}
