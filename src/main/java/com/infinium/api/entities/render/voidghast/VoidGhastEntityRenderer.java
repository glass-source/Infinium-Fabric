package com.infinium.api.entities.render.voidghast;

import com.infinium.Infinium;
import com.infinium.api.entities.mobs.voidmobs.VoidGhastEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidGhastEntityRenderer extends GeoEntityRenderer<VoidGhastEntity> {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/void_ghast/void_ghast.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/void_ghast/void_ghast_shooting.png");

    public VoidGhastEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new VoidGhastEntityModel());
    }

    @Override
    public Identifier getTexture(VoidGhastEntity ghastEntity) {
        return ghastEntity.isShooting() ? ANGRY_TEXTURE : TEXTURE;
    }
}
