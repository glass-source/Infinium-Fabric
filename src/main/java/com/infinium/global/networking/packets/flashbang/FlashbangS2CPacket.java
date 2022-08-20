package com.infinium.global.networking.packets.flashbang;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

public class FlashbangS2CPacket {

    private static final Identifier FLASHBANG = Infinium.id("textures/sanity/white_box.png");

    public static void receive(MinecraftClient minecraftClient, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        minecraftClient.execute(() -> {
            if (minecraftClient.player == null) return;
            var window = minecraftClient.getWindow();
            MatrixStack matrixStack = new MatrixStack();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, FLASHBANG);
            DrawableHelper.drawTexture(matrixStack, 0, 0, 0, 0, window.getScaledWidth(), window.getScaledHeight(), 1920, 1080);
            DrawableHelper.fill(matrixStack, 0, 0, window.getWidth(), window.getHeight(), 0xffffff);
        });
    }



}
