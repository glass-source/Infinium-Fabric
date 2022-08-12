package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SanityManager {

    private final ScheduledExecutorService service;
    public ArrayList<ServerPlayerEntity> totalPlayers = new ArrayList<>();
    private final Infinium instance;
    private final SanityTask task;

    public SanityManager(Infinium instance){
        this.instance = instance;
        this.service = instance.getExecutor();
        this.task = new SanityTask(this);
    }

    public void initSanityTask() {
        if (instance.getCore().getServer() == null) {
            System.out.println("null");
        } else {
            service.scheduleWithFixedDelay(task::run, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void addSanity(ServerPlayerEntity player, int amount) {
        setSanity(player, Math.max(0, Math.min(100, getSanity(player) + amount)));
    }

    public void removeSanity(ServerPlayerEntity player, int amount) {
        setSanity(player, Math.max(0, Math.min(100, getSanity(player) - amount)));
    }

    public int getSanity(ServerPlayerEntity player) {
        NbtCompound data = getData(player);
        return data.getInt("infinium.sanity");
    }

    public void setSanity(ServerPlayerEntity player, int amount) {
        NbtCompound data = getData(player);
        data.putInt("infinium.sanity", Math.max(0, Math.min(100,amount)));
    }


    private NbtCompound getData(ServerPlayerEntity entity){
        return ((EntityDataSaver) entity).getPersistentData();
    }
}
