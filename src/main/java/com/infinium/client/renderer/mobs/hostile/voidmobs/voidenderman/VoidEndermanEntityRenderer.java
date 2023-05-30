package com.infinium.client.renderer.mobs.hostile.voidmobs.voidenderman;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidEndermanEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidEndermanEntityRenderer extends GeoEntityRenderer<VoidEndermanEntity> {


    public VoidEndermanEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new VoidEndermanEntityModel());
    }

    @Override
    public Identifier getTexture(VoidEndermanEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_enderman/void_enderman.png");
    }
}
