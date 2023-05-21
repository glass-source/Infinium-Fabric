package com.infinium.client.renderer.mobs.hostile.bosses.supernova;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.server.entities.mobs.hostile.bosses.SuperNovaEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class SuperNovaEntityRenderer extends MobEntityRenderer<SuperNovaEntity, SuperNovaEntityModel<SuperNovaEntity>> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID ,"textures/entity/super_nova/super_nova.png");

    public SuperNovaEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new SuperNovaEntityModel<>(context.getPart(InfiniumModelLayers.SUPER_NOVA)), 1.0F);
    }

    protected int getBlockLight(SuperNovaEntity witherEntity, BlockPos blockPos) {
        return 15;
    }

    public Identifier getTexture(SuperNovaEntity witherEntity) {

        return TEXTURE;
    }

    protected void scale(SuperNovaEntity witherEntity, MatrixStack matrixStack, float f) {
        float g = 2.0F;
        int i = witherEntity.getInvulnerableTimer();
        if (i > 0) {
            g -= ((float)i - f) / 220.0F * 0.5F;
        }

        matrixStack.scale(g, g, g);
    }

}
