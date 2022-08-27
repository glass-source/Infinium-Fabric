package com.infinium.global.mixin.client.renderer.game;

import com.infinium.Infinium;
import com.infinium.networking.packets.flashbang.FlashbangManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Shadow @Final private MinecraftClient client;
    private int opacity = -25;

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnHead(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            FlashbangManager.render(matrices, (int) FlashbangManager.opacity, scaledWidth, scaledHeight, FlashbangManager.red, FlashbangManager.green, FlashbangManager.blue);
            if (Infinium.getInstance().getCore().getEclipseManager().isActive()) {
                if (opacity < 50) opacity++;
            } else {
                if (opacity > 0) opacity--;
            }
            DrawableHelper.fill(matrices, 0, 0, scaledWidth, scaledHeight, ColorHelper.Argb.getArgb(opacity, 0, 0, 50));
        }
    }

}
