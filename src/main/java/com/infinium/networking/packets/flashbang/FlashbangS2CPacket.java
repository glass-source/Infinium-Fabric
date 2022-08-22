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

    private static float opacity;
    private static int flashSeconds;
    private static int opaqueSeconds;
    private static int red;
    private static int green;
    private static int blue;
    private static PacketByteBuf packetByteBuf;

    public FlashbangS2CPacket(float opacity, int flashSeconds, int opaqueSeconds, int red, int green, int blue) {
        FlashbangS2CPacket.opacity = opacity;
        FlashbangS2CPacket.flashSeconds = flashSeconds;
        FlashbangS2CPacket.opaqueSeconds = opaqueSeconds;
        FlashbangS2CPacket.red = red;
        FlashbangS2CPacket.green = green;
        FlashbangS2CPacket.blue = blue;
        packetByteBuf = PacketByteBufs.create();
    }

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf buf, PacketSender packetSender) {
        FlashbangManager.reset();
        FlashbangManager.opacity = buf.readFloat();
        FlashbangManager.flashSeconds = buf.readInt();
        FlashbangManager.opaqueTicks = buf.readInt();
        FlashbangManager.red = buf.readInt();
        FlashbangManager.green = buf.readInt();
        FlashbangManager.blue = buf.readInt();

        MinecraftClient.getInstance().execute(() -> {
            var window = client.getWindow();
            if (window.getWidth() <= 0 && window.getHeight() <= 0) return;
            FlashbangManager.framebuffer = FlashbangManager.copyColorsFrom(client.getFramebuffer(), new SimpleFramebuffer(window.getWidth(), window.getHeight(), true, false));
            FlashbangManager.shouldTick = true;
        });


    }
    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        packetByteBuf.writeInt(opaqueSeconds);
        packetByteBuf.writeInt(red);
        packetByteBuf.writeInt(blue);
        packetByteBuf.writeInt(blue);
        return packetByteBuf;
    }

}
