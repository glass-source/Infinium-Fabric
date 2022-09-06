package com.infinium.server.entities.mobs.ghoulmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GhoulSpiderEntity extends SpiderEntity implements IAnimatable, InfiniumEntity {

    private final AnimationFactory factory = new AnimationFactory(this);

    public GhoulSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&cGhoul Spider"));
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

    public static DefaultAttributeContainer.Builder createGhoulSpiderAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.45f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20.0)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof LivingEntity entity) {
            StatusEffectInstance[] effects = {
            new StatusEffectInstance(StatusEffects.POISON, 200, 4),
            new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 1),
            new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0),
            new StatusEffectInstance(StatusEffects.HUNGER, 200, 9),
            };
            int randomNumber = random.nextInt(effects.length);
            entity.addStatusEffect(effects[randomNumber]);
        }

        return super.tryAttack(target);
    }

    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        var controller = e.getController();
        if (this.getTarget() != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_spider.tracked"));
            return PlayState.CONTINUE;

        } else if (e.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_spider.walk"));
            return PlayState.CONTINUE;

        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_spider.idle"));
            return PlayState.CONTINUE;
        }
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::state));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}
