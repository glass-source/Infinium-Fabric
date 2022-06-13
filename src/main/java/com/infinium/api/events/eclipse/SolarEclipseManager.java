package com.infinium.api.events.eclipse;

import com.infinium.Infinium;
import com.infinium.api.utils.ChatFormatter;
import com.infinium.client.InfiniumClient;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.world.GameRules;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class SolarEclipseManager {

    private static final String TITLE = ChatFormatter.format("&k| &6&l☀ &7&lEclipse Solar: &e&l%time% &6&l☀ &r&k|");
    public static final BossBar BOSS_BAR = BossBar.bossBar(Component.text(TITLE.replaceAll("%time%", "0:00:00")), 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_6);
    private static long endsIn;
    private static long lastTimeChecked;
    private static long totalTime = 0L;
    private static ScheduledExecutorService service;
    private static ScheduledFuture<?> task;

    private static void initService(){
        service = Infinium.getExecutor();
        task = service.scheduleWithFixedDelay(() -> {
            //if (isGamePaused()) return;
            var progress = (float) Math.max(0d, Math.min(1d, (double) getTimeToEnd() / getTotalTime()));
            var name = Component.text(ChatFormatter.format(TITLE.replaceAll("%time%", getTimeToString())));
            BOSS_BAR.name(name);
            BOSS_BAR.progress(progress);
            if (getTimeToEnd() <= 0){
                end();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void load() {
        lastTimeChecked = (new Date()).getTime();
        if (isActive()) {
            start(0.5);
        }
    }

    public static void start(double hours){
        if (hours <= 0) hours = 0.5;
        if (!isActive()) {
            initService();
            endsIn = 0L;
        }

        var server = Infinium.getServer();
        long addedTime = (long) (hours * 3600000.0D);

        endsIn += addedTime;
        totalTime += addedTime;
        lastTimeChecked = (new Date()).getTime();

        if (Infinium.getServer() != null) {
            var world = server.getOverworld();
            var audience = Infinium.getAdventure().audience(PlayerLookup.all(server));
            var times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2));
            var title = Title.title(Component.text(ChatFormatter.format("&8&k&l? &7Eclipse Solar &8&k&l?")), Component.text(ChatFormatter.format("&7Duración: &8" + getTimeToString())), times);
            audience.showTitle(title);
            audience.showBossBar(SolarEclipseManager.BOSS_BAR);
            audience.playSound(Sound.sound(Key.key("infinium:eclipse_start"), Sound.Source.PLAYER, 10, 0.5f));
            world.setTimeOfDay(18000);
            server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
        }
    }

    public static void end(){
        if (task != null) {
            task.cancel(true);
            task = null;
            service = null;
        }

        endsIn = 0L;
        totalTime = 0L;
        lastTimeChecked = 0;
        var server = Infinium.getServer();
        var audience = Infinium.getAdventure().audience(PlayerLookup.all(server));
        server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, Infinium.getServer());
        audience.hideBossBar(BOSS_BAR);
        audience.playSound(Sound.sound(Key.key("minecraft:item.trident.return"), Sound.Source.AMBIENT, 10, 0.05f));
    }

    public static long getTimeToEnd() {
        long now = (new Date()).getTime();
        endsIn -= now - lastTimeChecked;
        lastTimeChecked = now;
        return endsIn;
    }

    public static long getTotalTime() {
        return totalTime;
    }

    public static String getTimeToString() {
        if (isActive()) {
            long segs = getTimeToEnd() / 1000L;
            long days = segs / 86400L;
            long hours = segs % 86400L / 3600L;
            long mins = segs % 3600L / 60L;
            segs %= 60L;
            return (days > 0L ? String.format("%02d", days) + ":" : "") + String.format("%02d:%02d:%02d", hours, mins, segs);
        } else {
            return " ";
        }
    }

    public static boolean isActive() {
        return getTimeToEnd() > 0L;
    }

    public static boolean isGamePaused() {
        var server = Infinium.getServer();
        return server.isSingleplayer() && InfiniumClient.isPaused();
    }

}
