package com.infinium.global.mixin.client.renderer.game;

import com.infinium.Infinium;
import com.infinium.networking.packets.flashbang.FlashbangManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Shadow @Final private MinecraftClient client;

    @Shadow public abstract void render(MatrixStack matrices, float tickDelta);

    private int opacity = -25;

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnTail(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            if (client.player == null) return;
            var world = client.player.world;
            if (!world.getRegistryKey().equals(World.OVERWORLD)) return;
            FlashbangManager.render(matrices, (int) FlashbangManager.opacity, scaledWidth, scaledHeight, FlashbangManager.red, FlashbangManager.green, FlashbangManager.blue);
            if (Infinium.getInstance().getCore().getEclipseManager().isActive()) {
                if (opacity < 40) opacity++;
            } else {
                if (opacity > -24) opacity--;
            }
            DrawableHelper.fill(matrices, 0, 0, scaledWidth, scaledHeight, ColorHelper.Argb.getArgb(Math.max(opacity, 0), 50, 100, 150));
        }
    }

}
