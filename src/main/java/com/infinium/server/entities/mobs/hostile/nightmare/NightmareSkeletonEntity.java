package com.infinium.server.entities.mobs.hostile.nightmare;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.goals.global.InfiniumBowAttackGoal;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class NightmareSkeletonEntity extends SkeletonEntity implements InfiniumEntity {
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
        this.setCustomName(ChatFormatter.text("&cNightmare Skeleton"));

    }

    protected void initDataTracker() {
        super.initDataTracker();
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

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;

        } else if (source.isFire() ) {
            return false;

        } else {
            return super.damage(source, amount);
        }
    }

    public static DefaultAttributeContainer.Builder createNightmareSkeletonAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 45.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.30f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 60.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0);
    }

    public void tick() {
        super.tick();
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
}
