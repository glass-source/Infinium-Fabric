package com.infinium.client.renderer.mobs.hostile.voidmobs.voidenderman;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.voidmobs.voidenderman.VoidEndermanEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidEndermanEntityModel extends AnimatedGeoModel<VoidEndermanEntity> {

    @Override
    public Identifier getModelLocation(VoidEndermanEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/voidmobs/void_enderman/void_enderman.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidEndermanEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_enderman/void_enderman.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidEndermanEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/voidmobs/void_enderman/void_enderman.animation.json");
    }

    @Override
    public void setLivingAnimations(VoidEndermanEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
