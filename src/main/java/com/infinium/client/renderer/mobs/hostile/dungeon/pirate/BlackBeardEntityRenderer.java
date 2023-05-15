package com.infinium.client.renderer.mobs.hostile.dungeon.pirate;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumEvokerModel;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.util.Identifier;

public class BlackBeardEntityRenderer<T extends SpellcastingIllagerEntity> extends IllagerEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID ,"textures/entity/black_beard/black_beard.png");
    public BlackBeardEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfiniumEvokerModel<>(context.getPart(InfiniumModelLayers.INFINIUM_EVOKER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this) {
            public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T spellcastingIllagerEntity, float f, float g, float h, float j, float k, float l) {
                if (spellcastingIllagerEntity.isSpellcasting()) {
                    super.render(matrixStack, vertexConsumerProvider, i, spellcastingIllagerEntity, f, g, h, j, k, l);
                }

            }
        });
    }
    public Identifier getTexture(T spellcastingIllagerEntity) {
        return TEXTURE;
    }

}
