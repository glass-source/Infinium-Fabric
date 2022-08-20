package com.infinium.global.networking.packets.sanity;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class KairosCheckC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        var sanityManager = Infinium.getInstance().getCore().getSanityManager();
        sanityManager.set(player, (20 * 60) * 120, sanityManager.TIME_COOLDOWN);
        sanityManager.syncSanity(player, sanityManager.get(player, sanityManager.SANITY));
        player.sendMessage(ChatFormatter.textWithPrefix("&7Has revisado la hora!"), false);
    }

}
