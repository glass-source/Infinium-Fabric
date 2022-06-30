package com.infinium.client.renderer.voidghast;

import com.infinium.Infinium;
import com.infinium.global.entities.mobs.voidmobs.VoidGhastEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidGhastEntityModel extends AnimatedGeoModel<VoidGhastEntity> {


    @Override
    public Identifier getModelLocation(VoidGhastEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/voidghast.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidGhastEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_ghast/void_ghast.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidGhastEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/voidghast.animation.json");
    }
}
