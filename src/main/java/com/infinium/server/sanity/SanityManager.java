package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.networking.InfiniumPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SanityManager {
    private final SanityTask task;
    private final Infinium instance;
    public final String SANITY = "infinium.sanity";
    public final String TIME_COOLDOWN = "infinium.timeCooldown";
    public final String POSITIVE_HEALTH_COOLDOWN = "infinium.positiveHealth";
    public final String FULL_HUNGER_COOLDOWN = "infinium.fullHunger";
    public final String EMPTY_HUNGER_COOLDOWN = "infinium.emptyHunger";
    public final String NEGATIVE_HEALTH_COOLDOWN = "infinium.negativeHealth";
    public final String SOUND_COOLDOWN = "infinium.soundCooldown";
    public final String SOUND_POINTS = "infinium.soundPoints";
    public final String LOW_LIGHT_COOLDOWN = "infinium.lowlightCooldown";
    public final String HIGH_LIGHT_COOLDOWN = "infinium.highlightCooldown";

    public final String ENTITY_COOLDOWN = "infinium.entityCooldown";
    public final String BIOME_COOLDOWN = "infinium.biomeCooldown";

    private ScheduledExecutorService service;
    private ScheduledFuture<?> scheduledFuture;

    public SanityManager(final Infinium instance){
        this.instance = instance;
        this.service = this.instance.getExecutor();
        this.task = new SanityTask(this);
    }

    public void registerSanityTask(){
        if (service == null) service = instance.getExecutor();

        try {
            scheduledFuture = service.scheduleAtFixedRate(task::run, 0, 1000, TimeUnit.MILLISECONDS);
        } catch(Exception ignored){}
    }

    public void stopSanityTask() {
        if (scheduledFuture != null) {
            scheduledFuture.cancel(true);
            scheduledFuture = null;
            service = null;
        }
    }
    public void add(PlayerEntity player, int amount, String arg){
        var added = get(player, arg);
        set(player, added + amount, arg);
    }

    public void decrease(PlayerEntity player, int amount, String arg){
        var decreased = get(player, arg);
        set(player, decreased - amount, arg);
    }
    public void set(PlayerEntity player, int amount, String arg){
        NbtCompound data = getData(player);

        switch (arg) {
            case SANITY -> {
                var maxAmount = Math.max(0, Math.min(100, amount));
                data.putInt(arg, maxAmount);
                if ((player instanceof ServerPlayerEntity p)) syncSanity(p, maxAmount);
            }

            case SOUND_POINTS -> {
                var maxAmount = Math.max(-100, Math.min(100, amount));
                data.putInt(arg, maxAmount);
            }

            default -> data.putInt(arg, amount);
        }
    }

    public int get(PlayerEntity player, String arg) {
        NbtCompound data = getData(player);
        return data.getInt(arg);
    }
    public NbtCompound getData(PlayerEntity entity){
        return ((EntityDataSaver) entity).getPersistentData();
    }

    public void syncSanity(ServerPlayerEntity player, int amount){
        var buffer = PacketByteBufs.create();
        buffer.writeInt(amount);
        ServerPlayNetworking.send(player, InfiniumPackets.SANITY_SYNC_ID, buffer);
    }

}
