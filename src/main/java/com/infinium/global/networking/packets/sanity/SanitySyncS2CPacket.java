package com.infinium.global.networking.packets.sanity;

import com.infinium.global.utils.EntityDataSaver;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class SanitySyncS2CPacket {

    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        ((EntityDataSaver) minecraftClient.player).getPersistentData().putInt("infinium.sanity", packetByteBuf.readInt());
    }
}
