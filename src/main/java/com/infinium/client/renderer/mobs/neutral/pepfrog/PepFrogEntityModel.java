package com.infinium.client.renderer.mobs.neutral.pepfrog;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.neutral.PepFrogEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PepFrogEntityModel extends AnimatedGeoModel<PepFrogEntity> {

    @Override
    public Identifier getModelLocation(PepFrogEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/neutral/pep_frog/pep_frog.geo.json");

    }

    @Override
    public Identifier getTextureLocation(PepFrogEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/pep_frog/pep_frog.png");
    }

    @Override
    public Identifier getAnimationFileLocation(PepFrogEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/neutral/pep_frog/pep_frog.animation.json");
    }

    @Override
    public void setLivingAnimations(PepFrogEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
