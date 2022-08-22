package com.infinium.global.mixin.client.renderer.game;

import com.infinium.networking.packets.flashbang.FlashbangManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
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

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (client.options.hudHidden) {
            FlashbangManager.render(new MatrixStack(), (int) FlashbangManager.opacity, scaledWidth, scaledHeight, FlashbangManager.red, FlashbangManager.green, FlashbangManager.blue);
        }
    }

}
