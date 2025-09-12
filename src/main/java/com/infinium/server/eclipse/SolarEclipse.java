package com.infinium.server.eclipse;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.sounds.InfiniumSounds;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.title.Title;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.GameRules;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SolarEclipse {
    private final String bossBarTitle = ChatFormatter.format("&k| &6&l☀ &7&lEclipse Solar: &e&l%time% &6&l☀ &r&k|");
    private final ServerBossBar serverBossBar = new ServerBossBar(ChatFormatter.text(bossBarTitle.replaceAll("%time%", "0:00:00")), BossBar.Color.PURPLE, BossBar.Style.NOTCHED_6);
    private long endsIn;
    private long totalTime = 0L;
    private long lastTimeChecked;
    private ScheduledExecutorService service;
    private ScheduledFuture<?> task;
    private final SolarEclipseManager manager;

    public SolarEclipse(SolarEclipseManager manager) {
        this.manager = manager;
        this.service = Infinium.getInstance().getExecutor();
    }

    public void initBossBarTask(){

        if (service == null) service = Infinium.getInstance().getExecutor();

        try {
            Runnable runnable = () -> {
                var progress = (float) Math.max(0d, Math.min(1d, (double) getTimeToEnd() / getTotalTime()));
                var name = ChatFormatter.text(ChatFormatter.format(bossBarTitle.replaceAll("%time%", getTimeToString())));
                serverBossBar.setName(name);
                serverBossBar.setPercent(progress);

                if (getTimeToEnd() <= 0){
                    end();
                }
            };

            task = service.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);

        } catch (Exception ignored){}
    }

    public void start(double hours){
        if (Infinium.getInstance().getDateUtils() == null) return;
        if (hours <= 0) return;

        var core = manager.getInstance().getCore();
        if (core.getServer() == null) return;

        if (!isActive()) {
            initBossBarTask();
            endsIn = 0L;
        }

        long addedTime = (long) (hours * 3600000.0D);
        endsIn += addedTime;
        totalTime += addedTime;
        lastTimeChecked = (new Date()).getTime();

        var server = core.getServer();
        var world = server.getOverworld();
        var audience = core.getAdventure().audience(PlayerLookup.all(server));
        var times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2));
        var title = Title.title(ChatFormatter.stringToComponent("&8&k&l? &7Eclipse Solar &8&k&l?"), ChatFormatter.stringToComponent("&7Duración: &8" + getTimeToString()), times);
        var gamerules = server.getGameRules();

        serverBossBar.setVisible(true);
        world.setTimeOfDay(18000);
        audience.showTitle(title);
        gamerules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
        serverBossBar.setDarkenSky(true);
        core.getTotalPlayers().forEach(player -> {
            serverBossBar.addPlayer(player);
            player.playSound(InfiniumSounds.ECLIPSE_START, SoundCategory.AMBIENT, 1, 0.5f);
        });
    }

    public void end(){
        var core = manager.getInstance().getCore();
        var server = core.getServer();
        var gamerules = server.getGameRules();

        if (task != null) {
            task.cancel(true);
            task = null;
            service = null;
        }

        endsIn = 0L;
        totalTime = 0L;
        lastTimeChecked = 0;
        serverBossBar.setVisible(false);
        serverBossBar.clearPlayers();
        serverBossBar.setDarkenSky(false);
        gamerules.get(GameRules.DO_DAYLIGHT_CYCLE).set(true, server);
        core.getTotalPlayers().forEach(player -> player.playSound(SoundEvents.ITEM_TRIDENT_RETURN, SoundCategory.AMBIENT, 1, 0.05f));
    }
    public long getLastTimeChecked(){
        return lastTimeChecked;
    }
    public long getTimeToEnd() {
        long now = (new Date()).getTime();
        endsIn -= now - lastTimeChecked;
        lastTimeChecked = now;
        return endsIn;
    }
    public long getTotalTime() {
        return this.totalTime;
    }
    public long getLastChecked() {
        return this.lastTimeChecked;
    }
    public String getTimeToString() {
        if (isActive()) {
            long seconds = getTimeToEnd() / 1000L;
            long minutes = seconds % 3600L / 60L;
            long hours = seconds % 86400L / 3600L;
            long days = seconds / 86400L;
            seconds %= 60L;
            return (days > 0L ? String.format("%02d", days) + ":" : "") + String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return " ";
        }
    }
    public boolean isActive() {
        return getTimeToEnd() > 0L;
    }
    public ServerBossBar getBossBar() {
        return this.serverBossBar;
    }
    public void setEndsIn(long newValue) {
        this.endsIn = newValue;
    }
    public void setTotalTime(long newValue) {
        this.totalTime = newValue;
    }
    public void setLastTimeChecked(long newValue) {
        this.lastTimeChecked = newValue;
    }
    public long getEndsIn() {
        return this.endsIn;
    }

}