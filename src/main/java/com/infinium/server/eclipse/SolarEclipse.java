package com.infinium.server.eclipse;

import com.eliotlash.mclib.math.functions.limit.Min;
import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.networking.InfiniumPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.world.GameRules;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SolarEclipse {

    public static boolean isEclipseActive = false;
    public final String TITLE = ChatFormatter.format("&k| &6&l☀ &7&lEclipse Solar: &e&l%time% &6&l☀ &r&k|");
    public final BossBar BOSS_BAR = BossBar.bossBar(Component.text(TITLE.replaceAll("%time%", "0:00:00")), 1, BossBar.Color.PURPLE, BossBar.Overlay.NOTCHED_6);
    public long endsIn;
    public long totalTime = 0L;
    public long lastTimeChecked;
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
                var name = Component.text(ChatFormatter.format(TITLE.replaceAll("%time%", getTimeToString())));
                BOSS_BAR.name(name);
                BOSS_BAR.progress(progress);

                if (getTimeToEnd() <= 0){
                    end();
                }
            };

            task = service.scheduleAtFixedRate(runnable, 0, 1000, TimeUnit.MILLISECONDS);

        } catch(Exception ignored){}
    }

    public void startFromDeath() {
        int day = DateUtils.getDay();

        if(day == 0) {
            start(0.5D);

        }else if(day > 0 && day < 7 ){
            start(day);

        }else if(day == 7){
            start(0.5D);

        }else if(day > 7 && day < 14) {
            start(day - 7.5D);

        }else if(day == 14){
            start(0.5D);

        }else if(day > 14 && day < 21){
            start(day - 14.5D);

        }else if(day == 21){
            start(0.5D);

        }else if(day > 21 && day < 28){
            start(day - 21.5D);

        }else if(day == 28){
            start(0.5D);

        }else if(day > 28 && day < 35){
            start(day - 28.5D);

        }else if(day ==35){
            start(0.5D);

        }else if(day > 35 && day < 42){
            start(day - 35.5D);

        }else if(day == 42){
            start(0.5D);

        }else if(day > 42 && day < 49){
            start(day - 42.5D);

        }else if(day == 49){
            start(0.5D);

        }else if(day > 49 && day < 56){
            start(day - 49.5D);

        }else if(day == 56){
            start(0.5D);

        }else if(day > 56 && day < 63){
            start(day - 56.5D);

        }else if(day == 63){
            start(0.5D);

        }else if(day > 63 && day < 70){
            start(day - 63.5D);

        }else if(day == 70){
            start(0.5D);
        } else {
            start(new Random().nextDouble(0.5, 1.5));
        }
    }

    public void start(double hours){
        if (hours <= 0) return;
        if (!isActive()) {
            initBossBarTask();
            endsIn = 0L;
        }

        long addedTime = (long) (hours * 3600000.0D);
        endsIn += addedTime;
        totalTime += addedTime;
        lastTimeChecked = (new Date()).getTime();
        isEclipseActive = true;

        var day = DateUtils.getDay();
        var core = manager.getInstance().getCore();
        var server = core.getServer();

        if (server == null) return;
        var world = server.getOverworld();
        var audience = core.getAdventure().audience(PlayerLookup.all(server));
        var times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(5), Duration.ofSeconds(2));
        var title = Title.title(ChatFormatter.stringToComponent("&8&k&l? &7Eclipse Solar &8&k&l?"), ChatFormatter.stringToComponent("&7Duración: &8" + getTimeToString()), times);
        var gamerules = server.getGameRules();

        world.setTimeOfDay(18000);
        audience.showTitle(title);
        audience.showBossBar(BOSS_BAR);
        audience.playSound(Sound.sound(Key.key("infinium:eclipse_start"), Sound.Source.PLAYER, 10, 0.5f));
        gamerules.get(GameRules.DO_DAYLIGHT_CYCLE).set(false, server);
        if (day < 42) return;
        ChatFormatter.broadcastMessageWithPrefix("&7Se ha activado el modo &4UHC!");
        gamerules.get(GameRules.NATURAL_REGENERATION).set(false, server);
    }

    public void end(){
        var core = manager.getInstance().getCore();
        var server = core.getServer();
        var audience = core.getAdventure().audience(PlayerLookup.all(server));
        var gamerules = server.getGameRules();

        if (task != null) {
            task.cancel(true);
            task = null;
            service = null;
        }

        isEclipseActive = false;
        endsIn = 0L;
        totalTime = 0L;
        lastTimeChecked = 0;
        gamerules.get(GameRules.DO_DAYLIGHT_CYCLE).set(true, server);
        gamerules.get(GameRules.NATURAL_REGENERATION).set(true, server);
        audience.hideBossBar(BOSS_BAR);
        audience.playSound(Sound.sound(Key.key("minecraft:item.trident.return"), Sound.Source.AMBIENT, 10, 0.05f));
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
        return totalTime;
    }

    public String getTimeToString() {
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

    public boolean isActive() {
        return getTimeToEnd() > 0L;
    }



}