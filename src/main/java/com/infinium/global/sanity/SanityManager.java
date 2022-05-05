package com.infinium.global.sanity;

import com.infinium.Infinium;
import com.infinium.api.utils.EntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SanityManager {


    private static final ScheduledExecutorService service = Infinium.executorService;
    public static ArrayList<PlayerEntity> totalPlayers = new ArrayList<>();

    public static void initSanityTask() {
        service.scheduleWithFixedDelay(SanityTask::run, 0, 1, TimeUnit.SECONDS);
    }

    public static void stopSanityTask() {

    }

    public static void addSanity(PlayerEntity player, int i) {
        NbtCompound data = ((EntityDataSaver) player).getPersistentData();
        int cordura = data.getInt("infinium.cordura");
        data.putInt("infinium.cordura", Math.max(0, Math.min(100, cordura + i)));
    }

    public static void removeSanity(PlayerEntity player, int i) {
        NbtCompound data = ((EntityDataSaver) player).getPersistentData();
        int cordura = data.getInt("infinium.cordura");
        data.putInt("infinium.cordura", Math.max(0, Math.min(100, cordura - i)));
    }

}
