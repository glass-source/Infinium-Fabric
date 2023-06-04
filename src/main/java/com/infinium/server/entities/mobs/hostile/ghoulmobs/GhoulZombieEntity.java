package com.infinium.server.entities.mobs.hostile.ghoulmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.goals.global.IEntityAttackGoal;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class GhoulZombieEntity extends HostileEntity implements IAnimatable, InfiniumEntity {

    private static final TrackedData<Boolean> PLAYING_ATTACK_ANIMATION = DataTracker.registerData(GhoulZombieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final AnimationFactory factory = new AnimationFactory(this);

    public GhoulZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&cGhoul Zombie"));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(PLAYING_ATTACK_ANIMATION, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(3, new LookAtEntityGoal(this, MobEntity.class, 15.0F));
        this.goalSelector.add(2, new LookAtEntityGoal(this, PlayerEntity.class, 15.0F));
        this.goalSelector.add(2, new LookAroundGoal(this));
        this.goalSelector.add(1, new IEntityAttackGoal(this, 1.0f, false, PLAYING_ATTACK_ANIMATION));
        this.goalSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
        this.goalSelector.add(0, new SwimGoal(this));
    }

    public boolean isPlayingAttackAnimation() {
        return this.dataTracker.get(PLAYING_ATTACK_ANIMATION);
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
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 70.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (target instanceof LivingEntity entity) {
            StatusEffectInstance[] effects = {
            new StatusEffectInstance(StatusEffects.POISON, 200, 0),
            new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 0),
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
}
