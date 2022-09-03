package com.infinium.server.entities.mobs.ghoulmobs;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.EnumSet;

public class GhoulZombieEntity extends HostileEntity implements IAnimatable {

    private static final TrackedData<Boolean> PLAYING_ATTACK_ANIMATION = DataTracker.registerData(GhoulZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public GhoulZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PLAYING_ATTACK_ANIMATION, false);
    }

    public boolean isPlayingAttackAnimation() {
        return this.dataTracker.get(PLAYING_ATTACK_ANIMATION);
    }

    public void setPlayingAttackAnimation(boolean playingAttackAnimation) {
        this.dataTracker.set(PLAYING_ATTACK_ANIMATION, playingAttackAnimation);
    }
    protected void initGoals() {
        this.goalSelector.add(4, new SwimGoal(this));
        this.goalSelector.add(8, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(5, new LookAtEntityGoal(this, MobEntity.class, 16.0F));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        this.goalSelector.add(3, new LookAroundGoal(this));
        this.goalSelector.add(1, new GhoulZombieAttackGoal(this, 1.0f, false));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ZOMBIE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_DEATH;
    }
    protected SoundEvent getStepSound() {
        return SoundEvents.ENTITY_ZOMBIE_STEP;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    public EntityGroup getGroup() {
        return EntityGroup.UNDEAD;
    }

    public static DefaultAttributeContainer.Builder createGhoulZombieAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 75.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.40f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 30.0)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof LivingEntity entity) {
            StatusEffectInstance[] effects = {
            new StatusEffectInstance(StatusEffects.POISON, 200, 0),
            new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2),
            new StatusEffectInstance(StatusEffects.NAUSEA, 200, 4),
            new StatusEffectInstance(StatusEffects.HUNGER, 200, 14),
            };
            int randomNumber = random.nextInt(effects.length);
            entity.addStatusEffect(effects[randomNumber]);
        }

        return super.tryAttack(target);
    }

    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        var controller = e.getController();
         if (e.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_zombie.walk"));

         } else if (!isPlayingAttackAnimation()){
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_zombie.idle"));
         }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState playStatePredicateForAttack(AnimationEvent<E> event) {
        if (isPlayingAttackAnimation()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.ghoul_zombie.attack", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
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

    private static class GhoulZombieAttackGoal extends Goal {
        protected final GhoulZombieEntity mob;
        private final double speed;
        private final boolean pauseWhenMobIdle;
        private Path path;
        private double targetX;
        private double targetY;
        private double targetZ;
        private int updateCountdownTicks;
        private int coolDown;

        private long lastUpdateTime;

        public GhoulZombieAttackGoal(GhoulZombieEntity mob, double speed, boolean pauseWhenMobIdle) {
            this.mob = mob;
            this.speed = speed;
            this.pauseWhenMobIdle = pauseWhenMobIdle;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            long l = this.mob.world.getTime();
            if (l - this.lastUpdateTime < 20L) {
                return false;
            }
            this.lastUpdateTime = l;
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            this.path = this.mob.getNavigation().findPathTo(livingEntity, 0);
            if (this.path != null) {
                return true;
            }
            return this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return false;
            }
            if (!livingEntity.isAlive()) {
                return false;
            }
            if (!this.pauseWhenMobIdle) {
                return !this.mob.getNavigation().isIdle();
            }
            if (!this.mob.isInWalkTargetRange(livingEntity.getBlockPos())) {
                return false;
            }
            return !(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative();
        }

        @Override
        public void start() {
            this.mob.getNavigation().startMovingAlong(this.path, this.speed);
            this.updateCountdownTicks = 0;
            this.coolDown = 0;
            this.mob.setAttacking(true);
        }

        @Override
        public void stop() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (!EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(livingEntity)) {
                this.mob.setTarget(null);
            }
            this.mob.setAttacking(false);
            this.mob.setPlayingAttackAnimation(false);
            this.mob.getNavigation().stop();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity == null) {
                return;
            }
            this.mob.getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
            double d = this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            this.updateCountdownTicks = Math.max(this.updateCountdownTicks - 1, 0);
            if ((this.pauseWhenMobIdle || this.mob.getVisibilityCache().canSee(livingEntity)) && this.updateCountdownTicks <= 0 && (this.targetX == 0.0 && this.targetY == 0.0 && this.targetZ == 0.0 || livingEntity.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) >= 1.0 || this.mob.getRandom().nextFloat() < 0.05f)) {
                this.targetX = livingEntity.getX();
                this.targetY = livingEntity.getY();
                this.targetZ = livingEntity.getZ();
                this.updateCountdownTicks = 4 + this.mob.getRandom().nextInt(7);
                if (d > 1024.0) {
                    this.updateCountdownTicks += 10;
                } else if (d > 256.0) {
                    this.updateCountdownTicks += 5;
                }
                if (!this.mob.getNavigation().startMovingTo(livingEntity, this.speed)) {
                    this.updateCountdownTicks += 15;
                }
                this.updateCountdownTicks = this.getTickCount(this.updateCountdownTicks);
            }
            this.coolDown = Math.max(this.coolDown - 1, 0);
            this.mob.setPlayingAttackAnimation(this.coolDown <= 10 && this.mob.squaredDistanceTo(this.targetX, this.targetY, this.targetZ) <= 7.5);
            this.attack(livingEntity, d);
        }
        protected void attack(LivingEntity target, double squaredDistance) {
            double d = this.getSquaredMaxAttackDistance(target);
            if (squaredDistance <= d && this.coolDown <= 0) {
                this.mob.swingHand(Hand.MAIN_HAND);
                this.mob.tryAttack(target);
                this.resetCoolDown();
            }
        }
        protected void resetCoolDown() {
            this.coolDown = this.getTickCount(20);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return this.mob.getWidth() * 2.0f * (this.mob.getWidth() * 2.0f) + entity.getWidth();
        }
    }

}
