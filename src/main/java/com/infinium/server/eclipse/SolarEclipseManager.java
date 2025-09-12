package com.infinium.server.eclipse;

import com.infinium.Infinium;
import net.minecraft.entity.boss.ServerBossBar;

public class SolarEclipseManager {
    private final Infinium instance;
    private final SolarEclipse eclipse;
    private final ServerBossBar serverBossBar;
    private boolean canStartEclipse = true;
    public SolarEclipseManager(Infinium instance){
        this.instance = instance;
        this.eclipse = new SolarEclipse(this);
        this.serverBossBar = eclipse.getBossBar();
    }

    public void load() {
        if (this.instance.getCore() == null || this.instance.getCore().getServer() == null) {
            Infinium.getInstance().LOGGER.error("Server was null and couldn't load the solar eclipse data.");
        } else {
            var dataManager = this.instance.getCore().getDataManager();
            var gameData = dataManager.getGameData();

            if (!gameData.entrySet().isEmpty()) {
                eclipse.setEndsIn(gameData.get("endsIn").getAsLong());
                eclipse.setTotalTime(gameData.get("totalTime").getAsLong());
                eclipse.setLastTimeChecked(gameData.get("lastTimeChecked").getAsLong());
                setCanStartEclipse(gameData.get("canStartEclipse").getAsBoolean());

                if (eclipse.getEndsIn() > 0) {
                    start((double) (eclipse.getEndsIn() / 1000) / 3600);
                }
            }
            
        }
    }

    public void disable(){
        var dataManager = this.instance.getCore().getDataManager();
        var gameData = dataManager.getGameData();
        gameData.addProperty("endsIn", eclipse.getEndsIn());
        gameData.addProperty("totalTime", eclipse.getTotalTime());
        gameData.addProperty("lastTimeChecked", eclipse.getLastChecked());
        gameData.addProperty("canStartEclipse", getCanStart());
        dataManager.saveWorldData();
        eclipse.end();
    }

    public void start(double hours) {
        if (this.getCanStart()) eclipse.start(hours <= 0 ? 0.5 : hours);
    }

    public void end(){
        eclipse.end();
    }

    public String getTimeToString() {
        return eclipse.getTimeToString();
    }
    public boolean isActive() {
        return eclipse.getTimeToEnd() > 0L;
    }
    public ServerBossBar getBossBar(){
        return this.serverBossBar;
    }
    public Infinium getInstance(){
        return this.instance;
    }
    public long getTimeToEnd(){
        return eclipse.getTimeToEnd();
    }
    public long getTotalTime(){
        return eclipse.getTotalTime();
    }
    public long getLastTimeChecked(){
        return eclipse.getLastTimeChecked();
    }

    public long startFromLoad(){
        return (eclipse.getEndsIn() / 1000) / 3600;
    }
    public void setCanStartEclipse(boolean value) {
        this.canStartEclipse = value;
    }
    public boolean getCanStart() {
        return this.canStartEclipse;
    }

}
