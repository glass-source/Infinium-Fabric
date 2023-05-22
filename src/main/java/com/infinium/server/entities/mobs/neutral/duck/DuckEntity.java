package com.infinium.server.entities.mobs.neutral.duck;

import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.InfiniumEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class DuckEntity extends AnimalEntity implements IAnimatable, InfiniumEntity {

    /*
    ALL ANIMATIONS AND MODELS BELONG TO
    https://github.com/Okabintaro/UntitledDuckMod
    WE DON'T CLAIM OWNERSHIP OF THESE FILES, AND ARE MERELY BEING USED HERE AS AN EXAMPLE AND WILL NOT BE USED IN THE FINAL PRODUCT.
     */
    private final AnimationFactory factory = new AnimationFactory(this);
    private static final Ingredient BREEDING_INGREDIENT;
    public float flapProgress;
    public float maxWingDeviation;
    public float prevMaxWingDeviation;
    public float prevFlapProgress;
    public float flapSpeed = 1.0F;
    private float totalSpeed = 1.0F;
    public int eggLayTime;
    public boolean hasJockey;
    public DuckEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.eggLayTime = this.random.nextInt(6000) + 6000;
        this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
        this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }
    public static DefaultAttributeContainer.Builder createDuckAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18);
    }
    public void tickMovement() {
        super.tickMovement();
        this.prevFlapProgress = this.flapProgress;
        this.prevMaxWingDeviation = this.maxWingDeviation;
        this.maxWingDeviation += (this.onGround ? -1.0F : 4.0F) * 0.3F;
        this.maxWingDeviation = MathHelper.clamp(this.maxWingDeviation, 0.0F, 1.0F);
        if (!this.onGround && this.flapSpeed < 1.0F) {
            this.flapSpeed = 1.0F;
        }

        this.flapSpeed *= 0.9F;
        Vec3d vec3d = this.getVelocity();
        if (!this.onGround && vec3d.y < 0.0) {
            this.setVelocity(vec3d.multiply(1.0, 0.6, 1.0));
        }

        this.flapProgress += this.flapSpeed * 2.0F;
        if (!this.world.isClient && this.isAlive() && !this.isBaby() && !this.hasJockey() && --this.eggLayTime <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
            this.dropItem(Items.EGG);
            this.eggLayTime = this.random.nextInt(6000) + 6000;
        }

    }protected boolean hasWings() {
        return this.speed > this.totalSpeed;
    }
    protected void addFlapEffects() {
        this.totalSpeed = this.speed + this.maxWingDeviation / 2.0F;
    }
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_CHICKEN_AMBIENT;
    }
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_CHICKEN_HURT;
    }
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_CHICKEN_DEATH;
    }
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
    }
    public DuckEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return InfiniumEntityType.DUCK.create(serverWorld);
    }
    public boolean isBreedingItem(ItemStack stack) {
        return BREEDING_INGREDIENT.test(stack);
    }
    protected int getXpToDrop(PlayerEntity player) {
        return this.hasJockey() ? 10 : super.getXpToDrop(player);
    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.hasJockey = nbt.getBoolean("IsDuckJockey");
        if (nbt.contains("EggLayTime")) {
            this.eggLayTime = nbt.getInt("EggLayTime");
        }

    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("IsDuckJockey", this.hasJockey);
        nbt.putInt("EggLayTime", this.eggLayTime);
    }
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return this.hasJockey();
    }
    public void updatePassengerPosition(Entity passenger) {
        super.updatePassengerPosition(passenger);
        float f = MathHelper.sin(this.bodyYaw * 0.017453292F);
        float g = MathHelper.cos(this.bodyYaw * 0.017453292F);
        passenger.setPosition(this.getX() + (double)(0.1F * f), this.getBodyY(0.5) + passenger.getHeightOffset() + 0.0, this.getZ() - (double)(0.1F * g));
        if (passenger instanceof LivingEntity) {
            ((LivingEntity)passenger).bodyYaw = this.bodyYaw;
        }

    }
    public boolean hasJockey() {
        return this.hasJockey;
    }
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return this.isBaby() ? dimensions.height * 0.85F : dimensions.height * 0.92F;
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    }

    private <E extends IAnimatable> PlayState state(AnimationEvent<E> e) {
        var controller = e.getController();
        var animationBuilder = new AnimationBuilder();
        if (!this.isOnGround()) {
            controller.setAnimation(animationBuilder.addAnimation("fly"));
        } else if (this.touchingWater && !e.isMoving()) {
            controller.setAnimation(animationBuilder.addAnimation("idle_swim"));
        } else if (this.touchingWater) {
            controller.setAnimation(animationBuilder.addAnimation("swim"));
        } else if (e.isMoving()) {
            controller.setAnimation(animationBuilder.addAnimation("walk"));
        } else {
            controller.setAnimation(animationBuilder.addAnimation("idle"));
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
