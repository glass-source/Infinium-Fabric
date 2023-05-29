package com.infinium.networking.packets.sanity;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class KairosCheckC2SPacket {
    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        var sanityManager = Infinium.getInstance().getCore().getSanityManager();
        sanityManager.set(player, 600, sanityManager.TIME_COOLDOWN);
    }

}
