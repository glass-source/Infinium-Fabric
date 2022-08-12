package com.infinium.global.mixin.world;

import com.infinium.Infinium;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private static final Identifier VANILLA_MOON_PHASES = new Identifier("textures/environment/moon_phases.png");
    private static final Identifier ECLIPSE_MOON_PHASES = new Identifier(Infinium.MOD_ID, "textures/environment/moon_phases.png");
    @Mutable @Shadow @Final private static Identifier MOON_PHASES;


    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("HEAD"))
    private void renderEclipse(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci){
        MOON_PHASES =  Infinium.getInstance().getCore().getEclipseManager().isActive() ? ECLIPSE_MOON_PHASES : VANILLA_MOON_PHASES;
    }




}
