package com.infinium.api.events.eclipse;

import com.infinium.Infinium;
import com.infinium.api.utils.ChatFormatter;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.world.GameRules;

import java.time.Duration;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;



public class SolarEclipseManager {

    private static final String NAME = ChatFormatter.format("&5&k&? &6&l☀ &7&lEclipse Solar: &e&l%time% &6&l☀ &5&k&?");
    public static final BossBar BOSS_BAR = BossBar.bossBar(Component.text(NAME.replaceAll("%time%", "0:00:00")), 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_6);
    private static long endsIn;
    private static long lastTimeChecked;
    private static long totalTime = 0L;
    private static final ScheduledExecutorService service = Infinium.executorService;
    private static ScheduledFuture<?> task;

    private static void tick(){
        task = service.scheduleWithFixedDelay(() -> {
            var percent = (float) Math.max(0d, Math.min(1d, (double) getTimeToEnd() / getTotalTime()));
            var name = Component.text(ChatFormatter.format(NAME.replaceAll("%time%", getTime())));
            BOSS_BAR.name(name);
            BOSS_BAR.progress(percent);
            if (getTimeToEnd() <= 0){
                end();
            }

        }, 0, 1000, TimeUnit.MILLISECONDS);

    }

    public static void load() {
        lastTimeChecked = (new Date()).getTime();

        if (endsIn > 0L) {
            start(0.5);
        }
    }

    public static void start(double hours){
        if (hours <= 0) hours = 0.5;
        if (!isActive()) {
            tick();
            endsIn = 0L;
        }

        var server = Infinium.server;
        long addedTime = (long) (hours * 3600000.0D);

        endsIn += addedTime;
        totalTime += addedTime;
        lastTimeChecked = (new Date()).getTime();

        if (Infinium.server != null) {
            var world = server.getOverworld();
            var audience = Infinium.adventure.audience(PlayerLookup.all(server));
            var times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2));
            var title = Title.title(Component.text(ChatFormatter.format("&8&k&l? &7Eclipse Solar &8&k&l?")), Component.text(ChatFormatter.format("&7Duración: &8" + getTime())), times);
            audience.showTitle(title);
            audience.showBossBar(SolarEclipseManager.BOSS_BAR);
            audience.playSound(Sound.sound(Key.key("infinium:eclipse_start"), Sound.Source.PLAYER, 10, 0.5f));
            world.setTimeOfDay(18000);
            server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
        }
    }

    public static void end(){
        var server = Infinium.server;
        var audience = Infinium.adventure.audience(PlayerLookup.all(server));
        server.getGameRules().get(GameRules.DO_DAYLIGHT_CYCLE).set(true, Infinium.server);
        audience.hideBossBar(BOSS_BAR);
        audience.playSound(Sound.sound(Key.key("minecraft:item.trident.hit_ground"), Sound.Source.AMBIENT, 10, 0.003f));
        endsIn = 0L;
        totalTime = 0L;
        task.cancel(true);

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

    public static String getTime() {
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

}
