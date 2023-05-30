package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulspider;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GhoulSpiderEntityRenderer extends GeoEntityRenderer<GhoulSpiderEntity> {

    public GhoulSpiderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GhoulSpiderEntityModel());
    }

    @Override
    public Identifier getTexture(GhoulSpiderEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_spider/ghoul_spider.png");
    }

}
