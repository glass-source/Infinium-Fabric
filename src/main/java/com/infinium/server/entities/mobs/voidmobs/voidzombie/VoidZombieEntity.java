package com.infinium.server.entities.mobs.voidmobs.voidzombie;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
public class VoidZombieEntity extends HostileEntity implements IAnimatable, InfiniumEntity {
    private static final TrackedData<Boolean> PLAYING_ATTACK_ANIMATION = DataTracker.registerData(VoidZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    protected VoidZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&bVoid Zombie"));
    }
    public boolean isPlayingAttackAnimation() {
        return this.dataTracker.get(PLAYING_ATTACK_ANIMATION);
    }
    private <E extends IAnimatable> PlayState playStatePredicateForAttack(AnimationEvent<E> event) {
        if (isPlayingAttackAnimation()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.void_zombie.attack", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        var controller = e.getController();
        if (e.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_zombie.walk"));

        } else if (!isPlayingAttackAnimation()){
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_zombie.idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::state));
        animationData.addAnimationController(new AnimationController<>(this, "attackController", 0, this::playStatePredicateForAttack));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
