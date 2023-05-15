package com.infinium.client.renderer.mobs.hostile.raidmobs.berserker;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumVindicatorModel;
import com.infinium.server.entities.mobs.hostile.raidmobs.berserker.BerserkerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BerserkerEntityRenderer extends IllagerEntityRenderer<BerserkerEntity> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID ,"textures/entity/berserker/berserker.png");

    public BerserkerEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfiniumVindicatorModel<>(context.getPart(InfiniumModelLayers.VINDICATOR_INFINIUM)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer<>(this) {
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BerserkerEntity vindicatorEntity, float f, float g, float h, float j, float k, float l) {
                if (vindicatorEntity.isAttacking()) {
                    super.render(matrixStack, vertexConsumerProvider, i, vindicatorEntity, f, g, h, j, k, l);
                }

            }
        });
    }

    public Identifier getTexture(BerserkerEntity entity) {
        return TEXTURE;
    }
}
