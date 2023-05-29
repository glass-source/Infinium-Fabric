package com.infinium.global.mixin.client.renderer.world;

import com.infinium.Infinium;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    private final Identifier VANILLA_MOON_PHASES = new Identifier("textures/environment/moon_phases.png");
    private final Identifier ECLIPSE_MOON_PHASES = new Identifier(Infinium.MOD_ID, "textures/environment/moon_phases.png");
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At("TAIL"))
    private void renderSky(MatrixStack matrices, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo ci) {

        if (!bl) {
            if (this.client.world != null) {

                if (this.client.world.getDimensionEffects().getSkyType() == DimensionEffects.SkyType.NORMAL) {
                    Identifier MOON_PHASES = Infinium.getInstance().getCore().getEclipseManager().isActive() ? ECLIPSE_MOON_PHASES : VANILLA_MOON_PHASES;
                    BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                    Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
                    var k = 20.0F;
                    RenderSystem.setShaderTexture(0, MOON_PHASES);
                    int r = this.client.world.getMoonPhase();
                    int s = r % 4;
                    int m = r / 4 % 2;
                    float t = (float)(s) / 4.0F;
                    var o = (float)(m) / 2.0F;
                    var p = (float)(s + 1) / 4.0F;
                    var q = (float)(m + 1) / 2.0F;
                    bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
                    bufferBuilder.vertex(matrix4f2, -k, -100.0F, k).texture(p, q).next();
                    bufferBuilder.vertex(matrix4f2, k, -100.0F, k).texture(t, q).next();
                    bufferBuilder.vertex(matrix4f2, k, -100.0F, -k).texture(t, o).next();
                    bufferBuilder.vertex(matrix4f2, -k, -100.0F, -k).texture(p, o).next();
                    bufferBuilder.end();
                    BufferRenderer.draw(bufferBuilder);
                    RenderSystem.disableTexture();
                }
            }
        }



    }




}
