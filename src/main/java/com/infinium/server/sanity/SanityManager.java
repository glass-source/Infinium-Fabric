package com.infinium.server.sanity;

import com.infinium.Infinium;
import com.infinium.networking.InfiniumPackets;
import com.infinium.global.utils.EntityDataSaver;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;

public class SanityManager {
    public ArrayList<ServerPlayerEntity> totalPlayers = new ArrayList<>();
    private final SanityTask task;
    public final String SANITY = "infinium.sanity";
    public final String TIME_COOLDOWN = "infinium.timeCooldown";
    public final String POSITIVE_HEALTH_COOLDOWN = "infinium.positiveHealth";
    public final String NEGATIVE_HEALTH_COOLDOWN = "infinium.negativeHealth";

    public SanityManager(Infinium instance){
        this.task = new SanityTask(this);
    }

    public void registerSanityTask(){
        ServerTickEvents.START_SERVER_TICK.register(server -> task.run());
    }

    public void add(PlayerEntity player, int amount, String arg){
        if (get(player, TIME_COOLDOWN) <= 0) return;
        var added = get(player, arg);
        set(player, added + amount, arg);
    }

    public void decrease(PlayerEntity player, int amount, String arg){
        var decreased = get(player, arg);
        set(player, decreased - amount, arg);
    }

    public void set(PlayerEntity player, int amount, String arg){
        NbtCompound data = getData(player);
        data.putInt(arg, Math.max(0, Math.min(100, amount)));
        if (!(player instanceof ServerPlayerEntity)) return;
        if (arg.equals(SANITY)) syncSanity((ServerPlayerEntity) player, Math.max(0, Math.min(100, amount)));
    }

    public int get(PlayerEntity player, String arg){
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
