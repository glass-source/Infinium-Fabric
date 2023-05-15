package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulwitch;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulwitch.GhoulWitchEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class GhoulWitchEntityRenderer extends MobEntityRenderer<GhoulWitchEntity, GhoulWitchEntityModel> {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID,"textures/entity/ghoul_witch/ghoul_witch.png");

    public GhoulWitchEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new GhoulWitchEntityModel(context.getPart(InfiniumModelLayers.GHOUL_WITCH)), 0.5F);
    }

    public void render(GhoulWitchEntity witchEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(witchEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public Identifier getTexture(GhoulWitchEntity witchEntity) {
        return TEXTURE;
    }

    protected void scale(GhoulWitchEntity witchEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375F, 0.9375F, 0.9375F);
    }




}