package com.infinium.api.events.players;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

public class ServerPlayerConnectionEvents {

    public interface OnServerPlayerConnect {
        Event<OnServerPlayerConnect> EVENT = EventFactory.createArrayBacked(OnServerPlayerConnect.class,
                (listeners) -> (player) -> {

                    for (OnServerPlayerConnect listener : listeners) {
                        ActionResult result = listener.connect(player);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                });

        ActionResult connect(ServerPlayerEntity player);
    }

    public interface OnServerPlayerDisconnect {
        Event<OnServerPlayerDisconnect> EVENT = EventFactory.createArrayBacked(OnServerPlayerDisconnect.class,
                (listeners) -> (player) -> {

                    for (OnServerPlayerDisconnect listener : listeners) {
                        ActionResult result = listener.disconnect(player);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    } //Es necesario esto ? Porque el verificas el result si es pass returna pass pero si no returna el que no osea ??
                    return ActionResult.PASS;
                });

        ActionResult disconnect(ServerPlayerEntity player);
    }





}
