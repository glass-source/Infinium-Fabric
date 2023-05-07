package com.infinium.client.renderer.game.hud;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class SanityHudOverlay implements HudRenderCallback {

    private final Identifier SANITY_EMPTY = Infinium.id("textures/sanity/cordura_empty.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        
        var client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;
        var p = client.player;
        if (p.isSpectator() || p.isCreative()) return;
        var window = client.getWindow();
        var playerSanity = ((EntityDataSaver) p).getPersistentData().getInt("infinium.sanity");

        int scaledWidth = window.getScaledWidth();
        int scaledHeight = window.getScaledHeight() / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        RenderSystem.setShaderTexture(0, SANITY_EMPTY);
        DrawableHelper.drawTexture(matrixStack, scaledWidth - 54, scaledHeight - 74 + 10, 0, 0, 16, 48, 16, 48);
        DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, ChatFormatter.text(playerSanity + "%"), scaledWidth - 34, scaledHeight - 54 + (9), 0xffffff);
    }
}
