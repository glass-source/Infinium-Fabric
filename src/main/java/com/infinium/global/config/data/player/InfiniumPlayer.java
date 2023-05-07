package com.infinium.global.config.data.player;

import com.infinium.Infinium;
import com.infinium.global.config.data.JsonConfig;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.sanity.SanityManager;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.UUID;

public class InfiniumPlayer {

    private static HashMap<UUID, InfiniumPlayer> infiniumPlayers = new HashMap<>();
    private JsonConfig userJson;
    private final PlayerEntity player;

    private final SanityManager sanityManager = Infinium.getInstance().getCore().getSanityManager();
    
    public InfiniumPlayer(PlayerEntity player) {
        this.player = player;
        try {

            userJson = JsonConfig.userData(player.getEntityName()+".json");

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
        var data = ((EntityDataSaver) player).getPersistentData();
        var totemString = "infinium.totems";
        int totems = data.getInt(totemString);

        jsonConfig.setJsonObject(Infinium.getGson().toJsonTree(new UserData(player.getUuid(), player.getEntityName() ,sanityManager.get(player, sanityManager.SANITY), totems)).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void restore(JsonConfig jsonConfig)  {
        var data = ((EntityDataSaver) player).getPersistentData();
        var totemString = "infinium.totems";
        int totems = data.getInt(totemString);

        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            new UserData(player.getUuid(), player.getEntityName() ,sanityManager.get(player, sanityManager.SANITY), totems);
        } else {
            Infinium.getGson().fromJson(jsonConfig.getJsonObject(), UserData.class);
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
