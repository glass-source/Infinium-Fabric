package com.infinium.global.config.data.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.JsonConfig;
import com.infinium.server.listeners.player.PlayerConnectionListeners;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class InfiniumPlayer {

    private static HashMap<UUID, InfiniumPlayer> infiniumPlayers = new HashMap<>();
    private JsonConfig userJson;
    private final PlayerEntity player;
    private UserData userData;

    public InfiniumPlayer(PlayerEntity player) {
        this.player = player;
        try {
            userJson = JsonConfig.userData(player.getUuid()+".json");

        } catch (Exception ignored) {}
        InfiniumPlayer.getInfiniumPlayers().putIfAbsent(player.getUuid(), this);
    }

    public PlayerEntity getPlayer() {
        return this.player;

    }

    public static InfiniumPlayer getInfiniumPlayer(PlayerEntity p){
        if (!infiniumPlayers.containsKey(p.getUuid())) {
            return new InfiniumPlayer(p);
        } else {
            return infiniumPlayers.get(p.getUuid());
        }
    }

    public void onJoin() {
       restore(userJson);
    }

    public void onQuit() {
        save(userJson);
    }

    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(Infinium.getGson().toJsonTree(new UserData(player.getUuid())).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restore(JsonConfig jsonConfig)  {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.userData = new UserData(player.getUuid());
        } else {
            this.userData = Infinium.getGson().fromJson(jsonConfig.getJsonObject(), UserData.class);
        }
    }

    public static void setInfiniumPlayers(HashMap<UUID, InfiniumPlayer> infiniumPlayers) {
        InfiniumPlayer.infiniumPlayers = infiniumPlayers;
    }
    public static HashMap<UUID, InfiniumPlayer> getInfiniumPlayers() {
        return InfiniumPlayer.infiniumPlayers;
    }
    public JsonConfig getUserJson() {
        return this.userJson;
    }

}
