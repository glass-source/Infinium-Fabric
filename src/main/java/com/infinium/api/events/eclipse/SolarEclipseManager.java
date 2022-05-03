package com.infinium.api.events.eclipse;

import com.infinium.Infinium;
import com.infinium.api.utils.ChatFormatter;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;


//Lol this is no a event???????
public class SolarEclipseManager {

    private static final String NAME = ChatFormatter.format("&8&l☀ &7&lEclipse Solar: &e&l%time% &8&l☀");
    public static final BossBar BOSS_BAR = BossBar.bossBar(Component.text(NAME.replaceAll("%time%", "0:00:00")), 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_6);
    private static long endsIn;
    private static long lastTimeChecked;
    private static long totalTime = 0L;
    private static final ScheduledFuture<?> Task = null;


    private static void tick(){
        Infinium.executorService.scheduleWithFixedDelay(() -> {
            var percent = (float) Math.max(0d, Math.min(1d, (double) getTimeToEnd() / getTotalTime()));
            var name = Component.text(ChatFormatter.format(NAME.replaceAll("%time%", getTime())));
            BOSS_BAR.name(name);
            BOSS_BAR.progress(percent);
            if (getTimeToEnd() <= 0) end();

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
        long addedTime = (long) (hours * 3600000.0D);
        if (!isActive()) {
            tick();
            endsIn = 0L;

            if (Infinium.server != null) {
                Infinium.server.getOverworld().setTimeOfDay(18000);
                Infinium.adventure.audience(PlayerLookup.all(Infinium.server)).showBossBar(SolarEclipseManager.BOSS_BAR);
                PlayerLookup.all(Infinium.server).forEach(serverPlayerEntity -> {
                    serverPlayerEntity.playSound(SoundEvents.BLOCK_ENDER_CHEST_OPEN, 20, 0.002f);
                    serverPlayerEntity.sendMessage(Text.of("Activado!"), false);
                });
            } else {
                Infinium.LOGGER.debug("El servidor era null!");
            }


        }

        endsIn += addedTime;
        totalTime += addedTime;
        lastTimeChecked = (new Date()).getTime();
        if (Task != null) {
            Task.cancel(false);
        }
    }

    public static void end(){
        if (Task != null) {
            Task.cancel(true);
        }
        Infinium.adventure.audience(PlayerLookup.all(Infinium.server)).hideBossBar(BOSS_BAR);
        endsIn = 0L;
        totalTime = 0L;
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
