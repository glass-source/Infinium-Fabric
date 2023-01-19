package com.infinium.global.mixin.client.renderer.game;


import com.infinium.Infinium;
import com.infinium.networking.packets.flashbang.FlashbangManager;
import com.infinium.server.eclipse.SolarEclipse;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
    int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();
    @Shadow @Final private MinecraftClient client;
    private int opacity = -15;
    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnTail(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (client.options.hudHidden) {
            MatrixStack matrices = new MatrixStack();
            FlashbangManager.render(matrices, (int) FlashbangManager.opacity, scaledWidth, scaledHeight, FlashbangManager.red, FlashbangManager.green, FlashbangManager.blue);
            if (SolarEclipse.isEclipseActive) {
                if (opacity < 50) opacity++;
            } else {
                if (opacity > -14) opacity--;
            }
            DrawableHelper.fill(matrices, 0, 0, scaledWidth, scaledHeight, ColorHelper.Argb.getArgb(Math.max(opacity, 0), 50, 100, 200));
        }



    }

}
