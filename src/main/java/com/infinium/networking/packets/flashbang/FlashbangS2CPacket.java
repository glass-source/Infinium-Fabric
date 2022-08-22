package com.infinium.networking.packets.flashbang;

import com.infinium.global.utils.EntityDataSaver;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

public class FlashbangS2CPacket {

    private static @Getter float opacity;
    private static @Getter int flashSeconds;
    private static @Getter int opaqueSeconds;
    private static PacketByteBuf packetByteBuf;

    public FlashbangS2CPacket(float opacity, int flashSeconds, int opaqueSeconds) {
        FlashbangS2CPacket.opacity = opacity;
        FlashbangS2CPacket.flashSeconds = flashSeconds;
        FlashbangS2CPacket.opaqueSeconds = opaqueSeconds;
        packetByteBuf = PacketByteBufs.create();
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        FlashbangManager.reset();
        FlashbangManager.opacity = buf.readFloat();
        FlashbangManager.flashSeconds = buf.readInt();
        FlashbangManager.opaqueTicks = buf.readInt();
        MinecraftClient.getInstance().execute(() -> {
            if (client.getWindow().getWidth() > 0 && client.getWindow().getHeight() > 0)
                FlashbangManager.framebuffer = FlashbangManager.copyColorsFrom(client.getFramebuffer(), new SimpleFramebuffer(client.getWindow().getWidth(), client.getWindow().getHeight(), true, false));
            FlashbangManager.shouldTick = true;
        });


    }
    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        packetByteBuf.writeInt(opaqueSeconds);
        return packetByteBuf;
    }

}
