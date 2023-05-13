package com.infinium.server.entities.mobs.hostile.nightmare.nightmareskeleton;

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

public class NightmareSkeletonEntity extends SkeletonEntity implements InfiniumEntity {
    private static final TrackedData<Boolean> CONVERTING;
    private int onSnowTime;
    private int conversionTime;
    private final InfiniumBowAttackGoal<AbstractSkeletonEntity> bowAttackGoal = new InfiniumBowAttackGoal<>(this, 1.0, 20, 15.0F);
    private final MeleeAttackGoal meleeAttackGoal = new MeleeAttackGoal(this, 1.2, false) {
        public void stop() {
            super.stop();
            NightmareSkeletonEntity.this.setAttacking(false);
        }

        public void start() {
            super.start();
            NightmareSkeletonEntity.this.setAttacking(true);
        }
    };
    public NightmareSkeletonEntity(EntityType<? extends SkeletonEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(CONVERTING, false);
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
    @Nullable @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTransBanner(world, this);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(LocalDifficulty difficulty) {
        ItemStack magmaBow = new ItemStack(InfiniumItems.MAGMA_BOW);
        magmaBow.addEnchantment(Enchantments.POWER, 25);
        magmaBow.addEnchantment(Enchantments.PUNCH, 1);
        this.equipStack(EquipmentSlot.MAINHAND, magmaBow);
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0);
    }

    public static DefaultAttributeContainer.Builder createNightmareSkeletonAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 60.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0);
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
                    this.convertToVoid();
                }
            } else if (this.inPowderSnow) {
                ++this.onSnowTime;
                if (this.onSnowTime >= 140) {
                    this.setConversionTime(300);
                }
            } else {
                this.onSnowTime = -1;
            }
        }

        super.tick();
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("VoidConversionTime", this.isConverting() ? this.conversionTime : -1);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains("VoidConversionTime", 99) && nbt.getInt("VoidConversionTime") > -1) {
            this.setConversionTime(nbt.getInt("VoidConversionTime"));
        }

    }

    private void setConversionTime(int time) {
        this.conversionTime = time;
        this.dataTracker.set(CONVERTING, true);
    }

    /**
     * Converts this skeleton to a GHOUL and plays a sound if it is not silent.
     */
    protected void convertToVoid() {
        this.convertTo(InfiniumEntityType.VOID_SKELETON, true);
        if (!this.isSilent()) {
            this.world.syncWorldEvent(null, WorldEvents.SKELETON_CONVERTS_TO_STRAY, this.getBlockPos(), 0);
        }

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
        CONVERTING = DataTracker.registerData(NightmareSkeletonEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
