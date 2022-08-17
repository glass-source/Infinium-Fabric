package com.infinium.client.renderer.mobs.voidmobs.voidspider;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.voidmobs.VoidSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidSpiderEntityRenderer extends GeoEntityRenderer<VoidSpiderEntity> {


    public VoidSpiderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new VoidSpiderEntityModel());
    }

    @Override
    public Identifier getTexture(VoidSpiderEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_spider/void_spider.png");
    }
}
