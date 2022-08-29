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

    private static final Identifier SANITY_EMPTY = Infinium.id("textures/sanity/cordura_empty.png");
    private static final Identifier SANITY_FULL = Infinium.id("textures/sanity/cordura_full.png");
    private static final Identifier SANITY_PART_1 = Infinium.id("textures/sanity/cordura_part_1.png");
    private static final Identifier SANITY_PART_2 = Infinium.id("textures/sanity/cordura_part_2.png");
    private static final Identifier SANITY_PART_3 = Infinium.id("textures/sanity/cordura_part_3.png");

    @Override
    public void onHudRender(MatrixStack matrixStack, float tickDelta) {
        var client = MinecraftClient.getInstance();
        if (client == null || client.player == null) return;
        var p = client.player;
        if (p.isSpectator() || p.isCreative()) return;
        var window = client.getWindow();
        var s = ((EntityDataSaver) p).getPersistentData().getInt("infinium.sanity");

        int x = window.getScaledWidth();
        int y = window.getScaledHeight() / 2;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SANITY_EMPTY);
        DrawableHelper.drawTexture(matrixStack, x - 54, y - 74 + 10, 0, 0, 16, 48, 16, 48);
        DrawableHelper.drawTextWithShadow(matrixStack, client.textRenderer, ChatFormatter.text(s + "%"), x - 34, y - 54 + (9), 0xffffff);
    }
}
