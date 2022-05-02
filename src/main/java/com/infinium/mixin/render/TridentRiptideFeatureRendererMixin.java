package com.infinium.mixin.render;

import com.infinium.api.items.global.InfiniumItems;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.TridentRiptideFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.infinium.Infinium.id;

@Mixin(TridentRiptideFeatureRenderer.class)
public class TridentRiptideFeatureRendererMixin {

    @ModifyVariable(method = "render*", at = @At("STORE"))
    private VertexConsumer swapHotRiptide(VertexConsumer orig, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LivingEntity livingEntity) {
        if (livingEntity instanceof PlayerEntity && livingEntity.isUsingRiptide() && (livingEntity.getMainHandStack().getItem() == InfiniumItems.MAGMA_TRIDENT || (livingEntity.getOffHandStack().getItem() == InfiniumItems.MAGMA_TRIDENT))) {
            return vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(id("textures/entity/magma_trident_riptide.png")));
        }
        return orig;
    }

}
