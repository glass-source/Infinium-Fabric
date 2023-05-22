package com.infinium.client.renderer.mobs.neutral.duck;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.neutral.duck.DuckEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DuckEntityRenderer extends GeoEntityRenderer<DuckEntity> {
    public DuckEntityRenderer(EntityRendererFactory.Context context){
        super(context, new DuckEntityModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return Infinium.id("textures/entity/duck/duck.png");
    }
}
