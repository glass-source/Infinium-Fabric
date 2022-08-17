package com.infinium.client.renderer.mobs.voidmobs.voidspider;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.voidmobs.VoidSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class VoidSpiderEntityModel extends AnimatedGeoModel<VoidSpiderEntity> {


    @Override
    public Identifier getModelLocation(VoidSpiderEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/voidmobs/void_spider/void_spider.geo.json");
    }

    @Override
    public Identifier getTextureLocation(VoidSpiderEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/void_spider/void_spider.png");
    }

    @Override
    public Identifier getAnimationFileLocation(VoidSpiderEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/voidmobs/void_spider/void_spider.animation.json");
    }

    @Override
    public void setLivingAnimations(VoidSpiderEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData data = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (head == null) return;
        head.setRotationX(data.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(data.headPitch * ((float) Math.PI / 180F));
    }
}
