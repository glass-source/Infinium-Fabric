package com.infinium.client.renderer.mobs.neutral.pepfrog;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.neutral.PepFrogEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PepFrogEntityRenderer extends GeoEntityRenderer<PepFrogEntity> {

    public PepFrogEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PepFrogEntityModel());
    }

    @Override
    public Identifier getTexture(PepFrogEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/pep_frog/pep_frog.png");
    }
}
