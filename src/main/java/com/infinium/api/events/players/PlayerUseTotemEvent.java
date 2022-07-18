package com.infinium.api.events.players;

import com.infinium.api.world.Location;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;

public interface PlayerUseTotemEvent {

    Event<PlayerUseTotemEvent> EVENT = EventFactory.createArrayBacked(PlayerUseTotemEvent.class, (listeners) -> (player, totem) -> {

        for (PlayerUseTotemEvent listener : listeners) {
            ActionResult result = listener.use(player, totem);

            if (result != ActionResult.PASS) {
                return result;
            }
        }
        return ActionResult.PASS;
    });

    ActionResult use(PlayerEntity player, ItemStack totem);

}
