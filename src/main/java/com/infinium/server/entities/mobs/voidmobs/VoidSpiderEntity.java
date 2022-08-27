package com.infinium.server.entities.mobs.voidmobs;

import com.infinium.global.utils.ChatFormatter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class VoidSpiderEntity extends SpiderEntity implements IAnimatable {

    private final AnimationFactory factory = new AnimationFactory(this);

    public VoidSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&bVoid Spider"));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(6, new SwimGoal(this));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(4, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAroundGoal(this));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof LivingEntity entity) {
            StatusEffectInstance[] effects = {
                    new StatusEffectInstance(StatusEffects.WITHER, 200, 4),
                    new StatusEffectInstance(StatusEffects.BLINDNESS, 200, 0),
                    new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 4),
                    new StatusEffectInstance(StatusEffects.GLOWING, 200, 0),
            };
            int randomNumber = random.nextInt(effects.length);
            entity.addStatusEffect(effects[randomNumber]);
        }

        return super.tryAttack(target);
    }

    public static DefaultAttributeContainer.Builder createVoidSpiderAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 40.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
    }

    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        if (e.isMoving()) {
            e.getController().setAnimation(new AnimationBuilder().addAnimation("animation.void_spider.walk"));
            return PlayState.CONTINUE;
        }
        e.getController().setAnimation(new AnimationBuilder().addAnimation("animation.void_spider.idle"));
        return PlayState.CONTINUE;

    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::state));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    public static class TargetGoal<T extends LivingEntity> extends ActiveTargetGoal<T> {
        public TargetGoal(SpiderEntity spider, Class<T> targetEntityClass) {
            super(spider, targetEntityClass, true);
        }

        public boolean canStart() {
            return super.canStart();
        }
    }

    public static class AttackGoal extends MeleeAttackGoal {
        public AttackGoal(SpiderEntity spider) {
            super(spider, 1.0, true);
        }

        public boolean canStart() {
            return super.canStart() && !this.mob.hasPassengers();
        }

        public boolean shouldContinue() {
            float f = this.mob.getBrightnessAtEyes();
            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.shouldContinue();
            }
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return 4.0F + entity.getWidth();
        }
    }
}
