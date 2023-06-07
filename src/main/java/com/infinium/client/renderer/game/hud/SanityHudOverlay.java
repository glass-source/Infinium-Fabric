package com.infinium.client.renderer.game.hud;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SanityHudOverlay implements HudRenderCallback {
    private final Identifier SANITY_EMPTY = Infinium.id("textures/sanity/cordura2.png");
    private final Identifier SANITY_FULL = Infinium.id("textures/sanity/cordura1.png");
    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.options.debugEnabled) return;
        var p = client.player;
        if (p.isSpectator() || p.isCreative()) return;
        var window = client.getWindow();

        var playerSanity = ((EntityDataSaver) p).getPersistentData().getInt("infinium.sanity");
        int scaledWidth = window.getScaledWidth() / 3;
        int scaledHeight = (window.getScaledHeight() / 6);
        var color = playerSanity < 50 ? "&4" : "&6";

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 0.85F);

        var textureMatrix = new MatrixStack();
        var textMatrix = new MatrixStack();

        textureMatrix.scale(2.5f, 2.5f, 1.0f);
        textMatrix.scale(1.25f, 1.25f, 1.0f);
        DrawableHelper.drawTextWithShadow(textMatrix, client.textRenderer, ChatFormatter.text("🧠 &7[" + color + playerSanity + "%&7]"), (scaledWidth * 2) + 45, (scaledHeight * 2) - 25, 0xFFFFFF);
        renderBar(textureMatrix, scaledWidth + 25, scaledHeight, p);
    }

    private void renderBar(MatrixStack matrices, int x, int y, ClientPlayerEntity p) {
        RenderSystem.setShaderTexture(0, SANITY_EMPTY);
        DrawableHelper.drawTexture(matrices, x, y,-10 ,0, 0, 18, 34, 18, 34);
        var i = (((EntityDataSaver) p).getPersistentData().getInt("infinium.sanity") * 0.34);
        RenderSystem.setShaderTexture(0, SANITY_FULL);
        DrawableHelper.drawTexture(matrices, x, y, -10,0, 0, 18, (int) i, 18, 34);

    }
}
