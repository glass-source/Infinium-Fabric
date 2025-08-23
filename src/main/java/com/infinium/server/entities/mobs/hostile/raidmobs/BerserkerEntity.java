package com.infinium.server.entities.mobs.hostile.raidmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.NavigationConditions;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.function.Predicate;

public class BerserkerEntity extends VindicatorEntity implements InfiniumEntity {

    private static final Predicate<Difficulty> DIFFICULTY_ALLOWS_DOOR_BREAKING_PREDICATE = (difficulty) -> difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD;

    public BerserkerEntity(EntityType<? extends BerserkerEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&6Berserker"));
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new BerserkerBreakDoorGoal(this));
        this.goalSelector.add(2, new IllagerEntity.LongDoorInteractGoal(this));
        this.goalSelector.add(3, new RaiderEntity.PatrolApproachGoal(this, 10.0F));
        this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
    }

    protected void mobTick() {
        if (!this.isAiDisabled() && NavigationConditions.hasMobNavigation(this)) {
            boolean bl = ((ServerWorld)this.world).hasRaidAt(this.getBlockPos());
            ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(bl);
        }

        super.mobTick();
    }

    public static DefaultAttributeContainer.Builder createBerserkerAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 7.0);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        EntityData entityData2 = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        ((MobNavigation)this.getNavigation()).setCanPathThroughDoors(true);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);

        return entityData2;
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

    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
    }

    static class BerserkerBreakDoorGoal extends BreakDoorGoal {
        public BerserkerBreakDoorGoal(MobEntity mobEntity) {
            super(mobEntity, 6, BerserkerEntity.DIFFICULTY_ALLOWS_DOOR_BREAKING_PREDICATE);
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        public boolean shouldContinue() {
            BerserkerEntity BerserkerEntity = (BerserkerEntity)this.mob;
            return BerserkerEntity.hasActiveRaid() && super.shouldContinue();
        }

        public boolean canStart() {
            BerserkerEntity BerserkerEntity = (BerserkerEntity)this.mob;
            return BerserkerEntity.hasActiveRaid() && BerserkerEntity.random.nextInt(toGoalTicks(10)) == 0 && super.canStart();
        }

        public void start() {
            super.start();
            this.mob.setDespawnCounter(0);
        }
    }

    private static class BerserkerAttackGoal extends MeleeAttackGoal {
        public BerserkerAttackGoal(BerserkerEntity vindicator) {
            super(vindicator, 1.0, false);
        }

        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            if (this.mob.getVehicle() instanceof RavagerEntity) {
                float f = this.mob.getVehicle().getWidth() - 0.1F;
                return f * 2.0F * f * 2.0F + entity.getWidth();
            } else {
                return super.getSquaredMaxAttackDistance(entity);
            }
        }
    }


    
}
