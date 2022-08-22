package com.infinium.global.mixin.client.renderer.game;

import com.infinium.networking.packets.flashbang.FlashbangManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnHead(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            FlashbangManager.render(matrices, (int) FlashbangManager.opacity, scaledWidth, scaledHeight);
        }
    }

}
