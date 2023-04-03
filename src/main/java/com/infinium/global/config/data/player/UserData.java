package com.infinium.global.config.data.player;

import java.util.UUID;

public class UserData {

    private final String playerName;
    private final UUID playerUUID;
    private final int sanityValue;
    private final int totemsUsed;


    public UserData(UUID playerUUID, String playerName) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.sanityValue = 100;
        this.totemsUsed = 0;
    }

    public UserData(UUID playerUUID, String playerName, int sanityValue, int totemsUsed) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.sanityValue = sanityValue;
        this.totemsUsed = totemsUsed;
    }


    public String getPlayerName() {
        return this.playerName;
    }
    public UUID getPlayerUUID(){
        return this.playerUUID;
    }

    public int getSanityValue() {
        return this.sanityValue;
    }
    public int getTotemsUsed() {
        return this.totemsUsed;
    }

}
