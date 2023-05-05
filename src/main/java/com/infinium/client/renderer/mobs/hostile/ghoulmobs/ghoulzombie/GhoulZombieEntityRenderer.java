package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulzombie;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulzombie.GhoulZombieEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GhoulZombieEntityRenderer extends GeoEntityRenderer<GhoulZombieEntity> {

    public GhoulZombieEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GhoulZombieEntityModel());
    }

    @Override
    public Identifier getTexture(GhoulZombieEntity entity) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_zombie/ghoul_zombie.png");
    }

}
