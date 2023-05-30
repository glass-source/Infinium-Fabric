package com.infinium.server.entities.mobs.hostile.voidmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.goals.global.IEntityAttackGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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

public class VoidSpiderEntity extends SpiderEntity implements IAnimatable, InfiniumEntity {

    private static final TrackedData<Boolean> PLAYING_ATTACK_ANIMATION = DataTracker.registerData(VoidSpiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);
    public VoidSpiderEntity(EntityType<? extends SpiderEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&bVoid Spider"));
        this.navigation = createNavigation(this.world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PLAYING_ATTACK_ANIMATION, false);
    }
    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(2, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new IEntityAttackGoal(this, 1.0D, true, PLAYING_ATTACK_ANIMATION));
        this.goalSelector.add(1, new PounceAtTargetGoal(this, 0.4F));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
    }
    public boolean isPlayingAttackAnimation() {
        return this.dataTracker.get(PLAYING_ATTACK_ANIMATION);
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

    private <E extends IAnimatable> PlayState playStatePredicateForAttack(AnimationEvent<E> event) {
        if (isPlayingAttackAnimation()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.void_spider.attack", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData animationData) {
        animationData.addAnimationController(new AnimationController<>(this, "controller", 0, this::state));
        animationData.addAnimationController(new AnimationController<>(this, "attckController", 0, this::playStatePredicateForAttack));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }
}
