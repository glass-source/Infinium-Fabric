package com.infinium.client.listeners;

import com.infinium.client.events.ClientPlayerPauseEvent;
import net.minecraft.util.ActionResult;

public class ClientPlayerListeners {

    public static void registerListeners(){
        playerPauseCallback();
    }

    private static void playerPauseCallback(){
        ClientPlayerPauseEvent.EVENT.register(player -> {
            //InfiniumConfig.CLIENT_PAUSE_DATE = new Date().getTime();
            return ActionResult.PASS;
        });
    }

}
