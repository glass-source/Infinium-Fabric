package com.infinium.global.sanity;

import com.infinium.Infinium;
import com.infinium.api.listeners.player.ServerPlayerListeners;
import com.infinium.api.utils.EntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SanityManager {


    private static final ScheduledExecutorService service = Infinium.executorService;
    public static ArrayList<ServerPlayerEntity> totalPlayers = new ArrayList<>(); //Lool need?
    public static void initSanityTask() {
        service.scheduleWithFixedDelay(SanityTask::run, 0, 1, TimeUnit.SECONDS);
    }

    public static void stopSanityTask() {} //Lool need x2

    public static void addSanity(ServerPlayerEntity player, int amount) {

        SanityManager.setSanity(player, Math.max(0, Math.min(100, SanityManager.getSanity(player) + amount)));
    }

    public static void removeSanity(ServerPlayerEntity player, int amount) {
        SanityManager.setSanity(player, Math.max(0, Math.min(100, SanityManager.getSanity(player) - amount)));
    }




    public static int getSanity(ServerPlayerEntity entity) {
        NbtCompound data = ((EntityDataSaver) entity).getPersistentData();
        return data.getInt("infinium.sanity");
    }


    public static void setSanity(ServerPlayerEntity player, int amount) {
        NbtCompound data = ((EntityDataSaver) player).getPersistentData();
        data.putInt("infinium.sanity", Math.max(0, Math.min(100,amount)));
    }

}
