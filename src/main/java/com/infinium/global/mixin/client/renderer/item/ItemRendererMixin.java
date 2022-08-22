package com.infinium.global.mixin.client.renderer.item;

import com.infinium.Infinium;
import com.infinium.client.renderer.item.MagmaTridentItemRenderer;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow @Final private ItemModels models;

    @Inject(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), cancellable = true)
    public void renderItem(ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci){
        if (!stack.isEmpty() && stack.getItem() == InfiniumItems.MAGMA_TRIDENT) {
            matrices.push();
            boolean bl = renderMode == ModelTransformation.Mode.GUI || renderMode == ModelTransformation.Mode.GROUND || renderMode == ModelTransformation.Mode.FIXED;
            if (stack.getItem() == InfiniumItems.MAGMA_TRIDENT && bl) {
                model = ((ItemRendererAccesor) this).infinium$getModels().getModelManager().getModel(new ModelIdentifier(Infinium.MOD_ID + ":magma_trident#inventory"));
            }
            model.getTransformation().getTransformation(renderMode).apply(leftHanded, matrices);
            matrices.translate(-0.5D, -0.5D, -0.5D);
            if (model.isBuiltin() || stack.getItem() == InfiniumItems.MAGMA_TRIDENT && !bl) {
                MagmaTridentItemRenderer.render(stack, renderMode, matrices, vertexConsumers, light, overlay);
            } else {
                RenderLayer renderLayer = RenderLayers.getItemLayer(stack, true);
                VertexConsumer vertexConsumer4;
                vertexConsumer4 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, renderLayer, true, stack.hasGlint());

                ((ItemRendererAccesor) this).infinium$renderBakedItemModel(model, stack, light, overlay, matrices, vertexConsumer4);
            }

            matrices.pop();
            ci.cancel();
        }
    }


}
