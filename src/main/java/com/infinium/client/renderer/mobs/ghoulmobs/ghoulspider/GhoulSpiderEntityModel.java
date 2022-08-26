package com.infinium.client.renderer.mobs.ghoulmobs.ghoulspider;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.ghoulmobs.GhoulSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class GhoulSpiderEntityModel extends AnimatedGeoModel<GhoulSpiderEntity> {


    @Override
    public Identifier getModelLocation(GhoulSpiderEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/ghoulmobs/ghoul_spider/ghoul_spider.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GhoulSpiderEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_spider/ghoul_spider.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GhoulSpiderEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/ghoulmobs/ghoul_spider/ghoul_spider.animation.json");
    }

    @Override
    public void setLivingAnimations(GhoulSpiderEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }
}
