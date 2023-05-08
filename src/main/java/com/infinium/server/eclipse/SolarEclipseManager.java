package com.infinium.server.eclipse;

import com.infinium.Infinium;
import net.minecraft.entity.boss.ServerBossBar;

public class SolarEclipseManager {
    private final Infinium instance;
    private final SolarEclipse eclipse;
    private final ServerBossBar serverBossBar;

    public SolarEclipseManager(Infinium instance){
        this.instance = instance;
        this.eclipse = new SolarEclipse(this);
        this.serverBossBar = eclipse.serverBossBar;
    }

    public void load() {
        if (this.instance.getCore() == null || this.instance.getCore().getServer() == null) {
            Infinium.getInstance().LOGGER.error("Server was null");
        } else {
            var dataManager = this.instance.getCore().getDataManager();
            var gameData = dataManager.getGameData();

            if (!gameData.entrySet().isEmpty()) {
                eclipse.endsIn = gameData.get("endsIn").getAsLong();
                eclipse.totalTime = gameData.get("totalTime").getAsLong();
                eclipse.lastTimeChecked = gameData.get("lastTimeChecked").getAsLong();

                if (eclipse.endsIn > 0) {

                    start(startFromLoad());
                    serverBossBar.setVisible(true);
                    instance.getCore().getTotalPlayers().forEach(serverBossBar::addPlayer);
                }
            }
            
        }
    }

    public void disable(){
        var dataManager = this.instance.getCore().getDataManager();
        var gameData = dataManager.getGameData();
        gameData.addProperty("endsIn", eclipse.endsIn);
        gameData.addProperty("totalTime", eclipse.totalTime);
        gameData.addProperty("lastTimeChecked", eclipse.lastTimeChecked);
        dataManager.saveWorldData();
        eclipse.end();
    }



    public void start(double hours){
        if (hours <= 0) eclipse.start(0.5f);
        else eclipse.start(hours);
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
        return (eclipse.endsIn / 1000) / 3600;
    }

}
