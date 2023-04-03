package com.infinium.global.config.data.player;

import java.util.UUID;

public class UserData {

    private final UUID playerUUID;

    public UserData(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getPlayerUUID(){
        return this.playerUUID;
    }

}
