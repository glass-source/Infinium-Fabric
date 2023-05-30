package com.infinium.client.renderer.mobs.hostile.voidmobs.voidzombie;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.voidmobs.VoidZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class VoidZombieEntityModel extends AnimatedGeoModel<VoidZombieEntity> {

    @Override
    public Identifier getModelLocation(VoidZombieEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/voidmobs/void_zombie/void_zombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidZombieEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_zombie/void_zombie.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidZombieEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/voidmobs/void_zombie/void_zombie.animation.json");
    }

    @Override
    public void setLivingAnimations(VoidZombieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }



}
