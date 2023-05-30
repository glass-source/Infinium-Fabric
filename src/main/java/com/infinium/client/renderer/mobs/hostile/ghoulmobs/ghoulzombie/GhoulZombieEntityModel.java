package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulzombie;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.ghoulmobs.GhoulZombieEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class GhoulZombieEntityModel extends AnimatedGeoModel<GhoulZombieEntity> {

    @Override
    public Identifier getModelLocation(GhoulZombieEntity object) {
        return new Identifier(Infinium.MOD_ID, "geo/ghoulmobs/ghoul_zombie/ghoul_zombie.geo.json");
    }

    @Override
    public Identifier getTextureLocation(GhoulZombieEntity object) {
        return new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_zombie/ghoul_zombie.png");
    }

    @Override
    public Identifier getAnimationFileLocation(GhoulZombieEntity animatable) {
        return new Identifier(Infinium.MOD_ID, "animations/ghoulmobs/ghoul_zombie/ghoul_zombie.animation.json");
    }

    @Override
    public void setLivingAnimations(GhoulZombieEntity entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
    }



}
