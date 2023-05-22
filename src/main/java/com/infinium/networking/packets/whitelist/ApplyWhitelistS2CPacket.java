package com.infinium.networking.packets.whitelist;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class ApplyWhitelistS2CPacket {

    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        Infinium.getInstance().LOGGER.info("You are not whitelisted in this mod.");
        minecraftClient.stop();
    }

}
