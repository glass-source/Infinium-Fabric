package com.infinium.api.listeners.player;

import com.infinium.api.config.InfiniumConfig;
import com.infinium.api.events.players.PlayerPauseEvent;
import com.infinium.api.utils.ChatFormatter;
import net.minecraft.util.ActionResult;

import java.util.Date;

public class ClientPlayerListeners {

    public static void registerListeners(){
        playerPauseCallback();
    }

    private static void playerPauseCallback(){
        PlayerPauseEvent.EVENT.register(player -> {
            InfiniumConfig.set("client.pause.date", (new Date()).getTime());
            return ActionResult.PASS;
        });
    }

}
