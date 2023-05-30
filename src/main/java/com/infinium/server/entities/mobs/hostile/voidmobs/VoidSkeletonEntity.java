package com.infinium.server.entities.mobs.hostile.voidmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.goals.global.InfiniumBowAttackGoal;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

public class VoidSkeletonEntity extends SkeletonEntity implements InfiniumEntity {
    private static final TrackedData<Boolean> CONVERTING;
    private int onFireTime;
    private int conversionTime;

    private final InfiniumBowAttackGoal<AbstractSkeletonEntity> bowAttackGoal = new InfiniumBowAttackGoal<>(this, 1.0, 20, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            VoidSkeletonEntity.this.setAttacking(false);
        }

        public void start() {
            super.start();
            VoidSkeletonEntity.this.setAttacking(true);
        }
    };

    public VoidSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&bVoid Skeleton"));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(CONVERTING, false);
    }

    @Nullable @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTransBanner(world, this);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        ItemStack voidBow = new ItemStack(InfiniumItems.VOID_BOW);
        voidBow.addEnchantment(Enchantments.POWER, 35);
        voidBow.addEnchantment(Enchantments.PUNCH, 40);
        this.equipStack(EquipmentSlot.MAINHAND, voidBow);
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
    }

    public static DefaultAttributeContainer.Builder createVoidSkeletonAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 55.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 30.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 40.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(4, new InfiniumBowAttackGoal<>(this, 2.0, 15, 25.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void updateAttackType() {
        if (this.world != null && !this.world.isClient) {
            this.goalSelector.remove(this.meleeAttackGoal);
            this.goalSelector.remove(this.bowAttackGoal);

            if (isHoldingBow()) {

                if (this.bowAttackGoal != null) {
                    this.bowAttackGoal.setAttackInterval(15);
                    this.goalSelector.add(4, this.bowAttackGoal);
                }
            } else {
                if (this.meleeAttackGoal != null) this.goalSelector.add(4, this.meleeAttackGoal);
            }

        }
    }

    protected boolean isHoldingBow() {
        return this.isHolding(Items.BOW) || this.isHolding(InfiniumItems.VOID_BOW) || this.isHolding(InfiniumItems.MAGMA_BOW);
    }
    @Override
    public void tickMovement() {
        super.tickMovement();
    }

    public boolean isConverting() {
        return this.getDataTracker().get(CONVERTING);
    }

    public void setConverting(boolean converting) {
        this.dataTracker.set(CONVERTING, converting);
    }

    public boolean isShaking() {
        return this.isConverting();
    }

    public void tick() {
        if (!this.world.isClient && this.isAlive() && !this.isAiDisabled()) {
            if (this.isConverting()) {
                --this.conversionTime;
                if (this.conversionTime < 0) {
                    this.convertToGhoul();
                }
            } else if (this.isOnFire()) {
                ++this.onFireTime;
                if (this.onFireTime >= 140) {
                    this.setConversionTime(300);
                }
            } else {
                this.onFireTime = -1;
            }
        }

        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("GhoulConversionTime", this.isConverting() ? this.conversionTime : -1);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("GhoulConversionTime", 99) && nbt.getInt("GhoulConversionTime") > -1) {
            this.setConversionTime(nbt.getInt("GhoulConversionTime"));
        }

    }

    private void setConversionTime(int time) {
        this.conversionTime = time;
        this.dataTracker.set(CONVERTING, true);
    }

    /**
     * Converts this skeleton to a GHOUL and plays a sound if it is not silent.
     */
    protected void convertToGhoul() {
        this.convertTo(InfiniumEntityType.NIGHTMARE_SKELETON, true);
    }

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        Entity entity = source.getAttacker();
        if (entity instanceof CreeperEntity creeperEntity) {
            if (creeperEntity.shouldDropHead()) {
                creeperEntity.onHeadDropped();
                this.dropItem(Items.SKELETON_SKULL);
            }
        }

    }

    static {
        CONVERTING = DataTracker.registerData(VoidSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
