package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.networking.InfiniumPackets;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SanityManager {
    public ArrayList<ServerPlayerEntity> totalPlayers = new ArrayList<>();
    private final SanityTask task;
    private final Infinium instance;
    public final String SANITY = "infinium.sanity";
    public final String TIME_COOLDOWN = "infinium.timeCooldown";
    public final String POSITIVE_HEALTH_COOLDOWN = "infinium.positiveHealth";
    public final String NEGATIVE_HEALTH_COOLDOWN = "infinium.negativeHealth";
    public final String SOUND_COOLDOWN = "infinium.soundCooldown";

    public SanityManager(final Infinium instance){
        this.instance = instance;
        this.task = new SanityTask(this);
    }

    public void registerSanityTask(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(task::run, 1000, 1000, TimeUnit.MILLISECONDS);
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
        if (!arg.equals(SANITY)) {
            data.putInt(arg, amount);
        } else {
            var maxAmount = Math.max(0, Math.min(100, amount));
            data.putInt(arg, maxAmount);
            if (!(player instanceof ServerPlayerEntity)) return;
            syncSanity((ServerPlayerEntity) player, maxAmount);
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
