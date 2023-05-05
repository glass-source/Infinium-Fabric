package com.infinium.global.config.data;

import com.google.gson.JsonObject;
import com.infinium.Infinium;
import com.infinium.global.config.data.player.InfiniumPlayer;
import com.infinium.global.config.data.world.WorldData;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;

import java.time.LocalDate;

public class DataManager {

    private final Infinium infinium;
    private final InfiniumServerManager core;
    private final JsonConfig gameData;
    private WorldData worldData;
    private final SolarEclipseManager manager = Infinium.getInstance().getCore().getEclipseManager();

    public DataManager(final Infinium infinium) throws Exception {
        this.infinium = infinium;
        this.core = this.infinium.getCore();
        this.gameData = JsonConfig.config("WorldData.json");
    }
    public void savePlayerData() {
        core.getTotalPlayers().forEach(serverPlayerEntity -> {
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(serverPlayerEntity);
            infPlayer.save(infPlayer.getUserJson());
        });
    }

    public void restorePlayerData() {
        core.getTotalPlayers().forEach(serverPlayerEntity -> {
            var infPlayer = InfiniumPlayer.getInfiniumPlayer(serverPlayerEntity);
            infPlayer.restore(infPlayer.getUserJson());
        });
    }
    public void saveWorldData() {
        var startDate = core.getDateUtils().getStartDate().toString();

        gameData.setJsonObject(Infinium.getGson().toJsonTree(new WorldData(startDate, manager.getTimeToEnd(), manager.getTotalTime(), manager.getLastTimeChecked())).getAsJsonObject());
        try {

            gameData.save();

        } catch (Exception ex) {ex.printStackTrace();}
    }

    public void restoreWorldData() {
        if (getGameData().entrySet().isEmpty()) {
            String startDate = LocalDate.now().toString();
            this.worldData = new WorldData(startDate, 0, 0, 0);

        } else {
            this.worldData = Infinium.getGson().fromJson(getGameData(), WorldData.class);
        }

        try {
            gameData.setJsonObject(Infinium.getGson().toJsonTree(worldData).getAsJsonObject());
            gameData.save();

        } catch (Exception ex) {ex.printStackTrace();}

    }

    public JsonObject getGameData() {
        return this.gameData.getJsonObject();
    }



}
