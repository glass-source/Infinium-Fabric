package com.infinium.client.renderer.mobs.hostile.voidmobs.voidzombie;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidzombie.VoidZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class VoidZombieEntityRenderer extends GeoEntityRenderer<VoidZombieEntity> {

    public VoidZombieEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new VoidZombieEntityModel());
    }

    @Override
    public Identifier getTexture(VoidZombieEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_zombie/void_zombie.png");
    }

}
