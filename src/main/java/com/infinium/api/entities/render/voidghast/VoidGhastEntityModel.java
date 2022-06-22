package com.infinium.api.entities.render.voidghast;

import com.infinium.Infinium;
import com.infinium.api.entities.mobs.voidmobs.VoidGhastEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import java.util.Random;

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
