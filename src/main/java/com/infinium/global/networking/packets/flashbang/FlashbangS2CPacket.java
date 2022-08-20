package com.infinium.global.networking.packets.flashbang;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FlashbangS2CPacket {

    private static final Identifier FLASHBANG = Infinium.id("textures/sanity/white_box.png");

    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        renderOverlay(FLASHBANG, 1.0f);
    }

    private static void renderOverlay(Identifier texture, float opacity) {
        var scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
        var scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, opacity);
        RenderSystem.setShaderTexture(0, texture);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(0.0, scaledHeight, -90.0).texture(0.0f, 1.0f).next();
        bufferBuilder.vertex(scaledWidth, scaledHeight, -90.0).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(scaledWidth, 0.0, -90.0).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(0.0, 0.0, -90.0).texture(0.0f, 0.0f).next();
        tessellator.draw();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }

}
