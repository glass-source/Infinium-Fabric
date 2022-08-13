package com.infinium.server.eclipse;

import com.infinium.Infinium;
import net.kyori.adventure.bossbar.BossBar;

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
        if (eclipse.getTimeToEnd() > 0) {
            eclipse.start(eclipse.getTimeToEnd());
        }
    }

    public void disable(){
        eclipse.end();
    }

    public void startFromDeath(){
        eclipse.startFromDeath();
    }

    public void start(double hours){
        if (hours <= 0) eclipse.start(1);
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

}
