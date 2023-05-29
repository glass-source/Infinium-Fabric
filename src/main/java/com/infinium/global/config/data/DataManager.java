package com.infinium.global.config.data;

import com.google.gson.JsonObject;
import com.infinium.Infinium;
import com.infinium.global.config.data.world.WorldData;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;

import java.time.LocalDate;

public class DataManager {

    private final InfiniumServerManager core;
    private final JsonConfig gameData;
    private final SolarEclipseManager manager = Infinium.getInstance().getCore().getEclipseManager();

    public DataManager(final Infinium infinium) throws Exception {
        this.core = infinium.getCore();
        this.gameData = JsonConfig.config("WorldData.json");
    }

    public void saveWorldData() {
        var startDate = core.getDateUtils().getStartDate().toString();

        gameData.setJsonObject(Infinium.getGson().toJsonTree(new WorldData(startDate, manager.getTimeToEnd(), manager.getTotalTime(), manager.getLastTimeChecked())).getAsJsonObject());
        try {

            gameData.save();

        } catch (Exception ex) {ex.printStackTrace();}
    }

    public void restoreWorldData() {
        WorldData worldData;
        if (getGameData().entrySet().isEmpty()) {
            String startDate = LocalDate.now().toString();
            worldData = new WorldData(startDate, 0, 0, 0);

        } else {
            worldData = Infinium.getGson().fromJson(getGameData(), WorldData.class);
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
