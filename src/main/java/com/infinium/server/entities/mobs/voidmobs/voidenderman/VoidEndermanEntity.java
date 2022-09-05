package com.infinium.server.entities.mobs.voidmobs.voidenderman;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.mobs.voidmobs.voidenderman.goals.VoidEndermanChasePlayerGoal;
import com.infinium.server.entities.mobs.voidmobs.voidenderman.goals.VoidEndermanTeleportGoal;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.UUID;

public class VoidEndermanEntity extends HostileEntity implements IAnimatable, Angerable {
    private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
    private static final EntityAttributeModifier ATTACKING_SPEED_BOOST = new EntityAttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15000000596046448, EntityAttributeModifier.Operation.ADDITION);
    private static final TrackedData<Boolean> ANGRY = DataTracker.registerData(VoidEndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> PROVOKED = DataTracker.registerData(VoidEndermanEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private int lastAngrySoundAge = Integer.MIN_VALUE;
    private static final UniformIntProvider ANGER_TIME_RANGE = TimeHelper.betweenSeconds(20, 39);
    private int angerTime;
    @Nullable private UUID angryAt;

    private final AnimationFactory factory = new AnimationFactory(this);

    public VoidEndermanEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&bVoid Enderman"));
    }
    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ANGRY, false);
        this.dataTracker.startTracking(PROVOKED, false);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new VoidEndermanChasePlayerGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0, false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new VoidEndermanTeleportGoal(this, this::shouldAngerAt));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(4, new UniversalAngerGoal<>(this, false));
    }

    @Override
    public boolean hurtByWater() {
        return false;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ENDERMAN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDERMAN_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDERMAN_HURT;
    }

    public static DefaultAttributeContainer.Builder createVoidEndermanAttributes() {
        return HostileEntity.createHostileAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 80.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.37f)
        .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 45.0)
        .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 50.0);
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

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        super.setTarget(target);
        EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (target == null) {
            this.dataTracker.set(ANGRY, false);
            this.dataTracker.set(PROVOKED, false);
            entityAttributeInstance.removeModifier(ATTACKING_SPEED_BOOST);
        } else {
            this.dataTracker.set(ANGRY, true);
            if (!entityAttributeInstance.hasModifier(ATTACKING_SPEED_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(ATTACKING_SPEED_BOOST);
            }
        }

    }

    public boolean isAngry() {
        return this.dataTracker.get(ANGRY);
    }

    public boolean isProvoked() {
        return this.dataTracker.get(PROVOKED);
    }

    public void setProvoked() {
        this.dataTracker.set(PROVOKED, true);
    }

    @Override
    public void chooseRandomAngerTime() {
        this.setAngerTime(ANGER_TIME_RANGE.get(this.random));
    }
    @Override
    public void setAngerTime(int angerTime) {
        this.angerTime = angerTime;
    }

    @Override
    public int getAngerTime() {
        return this.angerTime;
    }

    @Override
    public void setAngryAt(@Nullable UUID angryAt) {
        this.angryAt = angryAt;
    }

    @Nullable
    public UUID getAngryAt() {
        return this.angryAt;
    }

    public void playAngrySound() {
        if (this.age >= this.lastAngrySoundAge + 400) {
            this.lastAngrySoundAge = this.age;
            if (!this.isSilent()) {
                this.world.playSound(this.getX(), this.getEyeY(), this.getZ(), SoundEvents.ENTITY_ENDERMAN_STARE, this.getSoundCategory(), 2.5F, 1.0F, false);
            }
        }
    }

    public boolean isPlayerStaring(PlayerEntity player) {
        Vec3d vec3d = player.getRotationVec(1.0F).normalize();
        Vec3d vec3d2 = new Vec3d(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
        double d = vec3d2.length();
        vec3d2 = vec3d2.normalize();
        double e = vec3d.dotProduct(vec3d2);
            return e > 1.0 - 0.025 / d && player.canSee(this);

    }
    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (ANGRY.equals(data) && this.isProvoked() && this.world.isClient) {
            this.playAngrySound();
        }
    }
    public void teleportRandomly() {
        if (!this.world.isClient() && this.isAlive()) {
            double d = this.getX() + (this.random.nextDouble() - 0.5) * 64.0;
            double e = this.getY() + (double)(this.random.nextInt(64) - 32);
            double f = this.getZ() + (this.random.nextDouble() - 0.5) * 64.0;
            this.teleportTo(d, e, f);
        }
    }

    public void teleportTo(double x, double y, double z) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, y, z);

        while(mutable.getY() > this.world.getBottomY() && !this.world.getBlockState(mutable).getMaterial().blocksMovement()) {
            mutable.move(Direction.DOWN);
        }

        BlockState blockState = this.world.getBlockState(mutable);
        boolean bl = blockState.getMaterial().blocksMovement();

        if (bl) {
            this.teleport(x, y, z, true);
            if (!this.isSilent()) {
                this.world.playSound(null, this.prevX, this.prevY, this.prevZ, SoundEvents.ENTITY_ENDERMAN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
                this.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
            }
        }
    }

    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        var controller = e.getController();
        if (this.isAngry() && e.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_enderman.track"));

        } else if (e.isMoving()) {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_enderman.walk"));

        } else {
            controller.setAnimation(new AnimationBuilder().addAnimation("animation.void_enderman.idle"));
        }
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



}
