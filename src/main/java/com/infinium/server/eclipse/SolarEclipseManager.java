package com.infinium.server.eclipse;

import com.infinium.Infinium;
import com.infinium.global.config.InfiniumConfig;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.bossbar.BossBar;
import net.minecraft.client.MinecraftClient;

public class SolarEclipseManager {
    private final Infinium instance;
    private final SolarEclipse eclipse;
    private final String TITLE;
    private final BossBar BOSS_BAR;

    public SolarEclipseManager(Infinium instance){
        this.instance = instance;
        this.eclipse = new SolarEclipse(this);
        this.TITLE = eclipse.TITLE;
        this.BOSS_BAR = eclipse.BOSS_BAR;
    }

    public void load() {
        if (instance.getCore().getServer() == null) {
            Infinium.getInstance().LOGGER.error("Server was null");
        } else {
            eclipse.lastTimeChecked = InfiniumConfig.lastTimeChecked;
            eclipse.endsIn = InfiniumConfig.endsIn;
            if (eclipse.endsIn > 0) {
                eclipse.totalTime = InfiniumConfig.totalTime;
                start(startFromLoad());
                var audience = instance.getCore().getAdventure().audience(PlayerLookup.all(instance.getCore().getServer()));
                audience.showBossBar(this.BOSS_BAR);
            }

            InfiniumConfig.write(Infinium.MOD_ID);
        }
    }

    public void disable(){
        InfiniumConfig.endsIn = getTimeToEnd();
        InfiniumConfig.totalTime = eclipse.totalTime;
        InfiniumConfig.lastTimeChecked = eclipse.lastTimeChecked;
        InfiniumConfig.write(Infinium.MOD_ID);
        eclipse.end();
    }

    public void startFromDeath(){
        eclipse.startFromDeath();
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

    public BossBar getBossBar(){
        return BOSS_BAR;
    }

    public String getTitle(){
        return TITLE;
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
