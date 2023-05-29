package com.infinium.server.entities.mobs.hostile.raidmobs.raider;

import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.BannerItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class RaiderEntity extends PillagerEntity implements InfiniumEntity {

    private static final TrackedData<Boolean> CHARGING;
    public RaiderEntity(EntityType<? extends PillagerEntity> entityType, World world) {
        super(entityType, world);
    }

    private final SimpleInventory inventory = new SimpleInventory(5);

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new net.minecraft.entity.raid.RaiderEntity.PatrolApproachGoal(this, 10.0F));
        this.goalSelector.add(3, new CrossbowAttackGoal<>(this, 1.0, 8.0F));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 15.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 15.0F));

        this.targetSelector.add(1, (new RevengeGoal(this, net.minecraft.entity.raid.RaiderEntity.class)).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createRaiderAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHARGING, false);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CHARGING);
    }
    @Override
    public void shoot(LivingEntity target, ItemStack crossbow, ProjectileEntity projectile, float multiShotSpray) {
        if (projectile instanceof ArrowEntity entity) {
            entity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));
            entity.addEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 60, 0));
            entity.setCritical(true);
            entity.setDamage(entity.getDamage() * 2);
            this.shoot(this, target, entity, multiShotSpray, 1.5F);
        } else {
            this.shoot(this, target, projectile, multiShotSpray, 1.5F);
        }
    }
    public void setCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        NbtList nbtList = new NbtList();

        for(int i = 0; i < this.getInventory().size(); ++i) {
            ItemStack itemStack = this.getInventory().getStack(i);
            if (!itemStack.isEmpty()) {
                nbtList.add(itemStack.writeNbt(new NbtCompound()));
            }
        }

        nbt.put("Inventory", nbtList);
    }

    public IllagerEntity.State getState() {
        if (this.isCharging()) {
            return IllagerEntity.State.CROSSBOW_CHARGE;
        } else if (this.isHolding(Items.CROSSBOW)) {
            return IllagerEntity.State.CROSSBOW_HOLD;
        } else {
            return this.isAttacking() ? IllagerEntity.State.ATTACKING : IllagerEntity.State.NEUTRAL;
        }
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        NbtList nbtList = nbt.getList("Inventory", 10);

        for(int i = 0; i < nbtList.size(); ++i) {
            ItemStack itemStack = ItemStack.fromNbt(nbtList.getCompound(i));
            if (!itemStack.isEmpty()) {
                this.inventory.addStack(itemStack);
            }
        }

        this.setCanPickUpLoot(true);
    }

    public int getLimitPerChunk() {
        return 4;
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);
        this.setTransBanner(world, this);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    protected void enchantMainHandItem(float power) {
        ItemStack itemStack = this.getMainHandStack();
        if (EnchantmentTarget.CROSSBOW.isAcceptableItem(itemStack.getItem())) {
            if (itemStack.isOf(Items.CROSSBOW)) {
                Map<Enchantment, Integer> map = EnchantmentHelper.get(itemStack);
                map.putIfAbsent(Enchantments.PIERCING, 3);
                EnchantmentHelper.set(map, itemStack);
                this.equipStack(EquipmentSlot.MAINHAND, itemStack);
            }
            super.enchantMainHandItem(power);
        }

    }

    public boolean isTeammate(Entity other) {
        if (super.isTeammate(other)) {
            return true;
        } else if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        } else {
            return false;
        }
    }

    public Inventory getInventory() {
        return this.inventory;
    }


    protected void loot(ItemEntity item) {
        ItemStack itemStack = item.getStack();
        if (itemStack.getItem() instanceof BannerItem) {
            super.loot(item);
        } else if (this.isRaidCaptain(itemStack)) {
            this.triggerItemPickedUpByEntityCriteria(item);
            ItemStack itemStack2 = this.inventory.addStack(itemStack);
            if (itemStack2.isEmpty()) {
                item.discard();
            } else {
                itemStack.setCount(itemStack2.getCount());
            }
        }

    }

    private boolean isRaidCaptain(ItemStack stack) {
        return this.hasActiveRaid() && stack.isOf(Items.WHITE_BANNER);
    }

    public StackReference getStackReference(int mappedIndex) {
        int i = mappedIndex - 300;
        return i >= 0 && i < this.getInventory().size() ? StackReference.of(this.getInventory(), i) : super.getStackReference(mappedIndex);
    }

    static {
        CHARGING = DataTracker.registerData(RaiderEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }



}
