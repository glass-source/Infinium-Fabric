package com.infinium.client.renderer.mobs.hostile.voidmobs.voidghast;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidghast.VoidGhastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VoidGhastEntityRenderer extends MobEntityRenderer<VoidGhastEntity, VoidGhastEntityModel>{

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/void_ghast/void_ghast.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/void_ghast/void_ghast_shooting.png");
    public static final EntityModelLayer VOID_GHAST = new EntityModelLayer(new Identifier(Infinium.MOD_ID, "void_ghast"), "void_ghast");

    public VoidGhastEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidGhastEntityModel(context.getPart(VOID_GHAST)), 1.5F);
    }

    @Override
    public Identifier getTexture(VoidGhastEntity ghastEntity) {
        return ghastEntity.isShooting() ? ANGRY_TEXTURE : TEXTURE;
    }

    @Override
    protected void scale(VoidGhastEntity ghastEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(4.5F, 4.5F, 4.5F);
    }
}
