package com.infinium.client.renderer;

import com.infinium.client.renderer.magmatrident.MagmaTridentItemRenderer;
import com.infinium.global.items.groups.InfiniumItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.BuiltinModelItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class InfiniumBuiltinItemModelRenderer extends BuiltinModelItemRenderer {

    public InfiniumBuiltinItemModelRenderer(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelLoader entityModelLoader) {
        super(blockEntityRenderDispatcher, entityModelLoader);
    }

    @Override
    public void render(ItemStack itemStack, ModelTransformation.Mode transformType, MatrixStack poseStack, VertexConsumerProvider multiBufferSource, int i, int j) {
        if (itemStack.getItem() == InfiniumItems.MAGMA_TRIDENT) {
            MagmaTridentItemRenderer.render(itemStack, transformType, poseStack, multiBufferSource, i, j);
        } else if (itemStack.getItem() == InfiniumItems.MAGMA_SHIELD) {
            //MagmaShieldItemRenderer.render(itemStack, transformType, poseStack, multiBufferSource, i, j);
        } else {
            super.render(itemStack, transformType, poseStack, multiBufferSource, i, j);
        }
    }
}