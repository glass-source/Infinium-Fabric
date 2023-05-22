package com.infinium.client.renderer.mobs.neutral.duck;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.neutral.duck.DuckEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DuckEntityModel extends AnimatedGeoModel<DuckEntity> {

    @Override
    public Identifier getModelLocation(DuckEntity object) {
        return Infinium.id("geo/neutral/duck.geo.json");
    }

    @Override
    public Identifier getTextureLocation(DuckEntity object) {
        return Infinium.id("textures/entity/duck/duck.png");
    }

    @Override
    public Identifier getAnimationFileLocation(DuckEntity animatable) {
        return Infinium.id("animations/neutral/duck.animation.json");
    }

    @Override
    public void setLivingAnimations(DuckEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);

        if (entity.isBaby()) {
            IBone root = this.getAnimationProcessor().getBone("root");
            if (root != null) {
                root.setScaleX(0.7f);
                root.setScaleY(0.7f);
                root.setScaleZ(0.7f);
            }

        }
        IBone head = this.getAnimationProcessor().getBone("head");
        if (entity.getLookControl().method_38970() && head != null) {
            head.setRotationX(entity.getPitch() * ((float) Math.PI / 180F));
            head.setRotationY(entity.getHeadYaw() * ((float) Math.PI / 180F));
        }
    }
}
