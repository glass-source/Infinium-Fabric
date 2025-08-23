package com.infinium.server.entities.mobs.hostile.dungeon.pirate;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.mobs.hostile.raidmobs.BerserkerEntity;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlackBeardEntity extends SpellcastingIllagerEntity implements InfiniumEntity {
    public BlackBeardEntity(EntityType<? extends BlackBeardEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&cBlack Beard"));
        this.experiencePoints = 450;
    }

    public static DefaultAttributeContainer.Builder createBlackBeardAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 20.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 220.0);
    }

    public void onDeath(DamageSource source) {
        super.onDeath(source);
        this.dropStack(InfiniumItems.LOOTING_MERGER.getDefaultStack());
        this.dropStack(InfiniumItems.VOID_TOTEM.getDefaultStack());
    }
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(0, new FleeEntityGoal<>(this, PlayerEntity.class, 12.0F, 0.2, 0.4));
        this.goalSelector.add(3, new SummonSkeletonsGoal());
        this.goalSelector.add(2, new ConjureSkeletonsGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(10, new LookAtEntityGoal(this, LivingEntity.class, 8.0F));
        this.goalSelector.add(0, new LookAtTargetGoal());
        this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
        this.targetSelector.add(2, (new ActiveTargetGoal<>(this, PlayerEntity.class, true)));
    }


    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    @Override
    public State getState() {
        return super.getState();
    }

    protected void initDataTracker() {
        super.initDataTracker();
    }

    @Override
    public void addBonusForWave(int wave, boolean unused) {

    }
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }
    protected void mobTick() {
        super.mobTick();
    }
    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }
    public boolean isTeammate(Entity other) {
        if (other == null) {
            return false;
        } else if (other == this) {
            return true;
        } else if (super.isTeammate(other)) {
            return true;
        } else if (other instanceof VexEntity) {
            return this.isTeammate(((VexEntity)other).getOwner());
        } else if (other instanceof LivingEntity && ((LivingEntity)other).getGroup() == EntityGroup.ILLAGER) {
            return this.getScoreboardTeam() == null && other.getScoreboardTeam() == null;
        } else return other instanceof PirateSkeletonEntity;
    }
    private class SummonSkeletonsGoal extends SpellcastingIllagerEntity.CastSpellGoal {

        private final TargetPredicate closeSkeletonPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().ignoreDistanceScalingFactor();

        SummonSkeletonsGoal() {
            super();
        }

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int i = BlackBeardEntity.this.world.getTargets(PirateSkeletonEntity.class, this.closeSkeletonPredicate, BlackBeardEntity.this, BlackBeardEntity.this.getBoundingBox().expand(16.0)).size();
                return BlackBeardEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld) BlackBeardEntity.this.world;
            for(int i = 0; i < new Random().nextInt(4); ++i) {
                BlockPos blockPos = BlackBeardEntity.this.getBlockPos().add(-2 + BlackBeardEntity.this.random.nextInt(5), 1, -2 + BlackBeardEntity.this.random.nextInt(5));
                PirateSkeletonEntity pirateSkeletonEntity = InfiniumEntityType.PIRATE_SKELETON.create(BlackBeardEntity.this.world);
                assert pirateSkeletonEntity != null;
                pirateSkeletonEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                pirateSkeletonEntity.initialize(serverWorld, BlackBeardEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                serverWorld.spawnEntityAndPassengers(pirateSkeletonEntity);
            }
        }

        public void tick() {
            super.tick();
        }

        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        protected Spell getSpell() {

            return Spell.SUMMON_VEX;
        }
    }

    private class ConjureSkeletonsGoal extends CastSpellGoal {

        private final TargetPredicate closeSkeletonPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().ignoreDistanceScalingFactor();
        ConjureSkeletonsGoal() {
            super();
        }

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int i = BlackBeardEntity.this.world.getTargets(BerserkerEntity.class, this.closeSkeletonPredicate, BlackBeardEntity.this, BlackBeardEntity.this.getBoundingBox().expand(16.0)).size();
                return BlackBeardEntity.this.random.nextInt(8) + 1 > i;
            }
        }
        protected int getSpellTicks() {
            return 40;
        }

        protected int startTimeDelay() {
            return 100;
        }

        protected void castSpell() {
            LivingEntity livingEntity = BlackBeardEntity.this.getTarget();
            assert livingEntity != null;
            double e = Math.max(livingEntity.getY(), BlackBeardEntity.this.getY()) + 1.0;
            float f = (float) MathHelper.atan2(livingEntity.getZ() - BlackBeardEntity.this.getZ(), livingEntity.getX() - BlackBeardEntity.this.getX());
            int i;
            if (BlackBeardEntity.this.squaredDistanceTo(livingEntity) < 9.0) {
                float g;
                for(i = 0; i < 3; ++i) {
                    g = f + (float)i * 3.14f * 0.4F;
                    this.conjureBerserkers(BlackBeardEntity.this.getX() + (double)MathHelper.cos(g) * 1.5, BlackBeardEntity.this.getZ() + (double)MathHelper.sin(g) * 1.5, e);
                }

                for(i = 0; i < 3; ++i) {
                    g = f + (float)i * 3.14F * 2.0F / 8.0F + 1.25F;
                    this.conjureBerserkers(BlackBeardEntity.this.getX() + (double)MathHelper.cos(g) * 2.5, BlackBeardEntity.this.getZ() + (double)MathHelper.sin(g) * 2.5, e);
                }
            } else {
                for(i = 0; i < 3; ++i) {
                    double h = 1.25 * (double)(i + 1);
                    this.conjureBerserkers(BlackBeardEntity.this.getX() + (double)MathHelper.cos(f) * h, BlackBeardEntity.this.getZ() + (double)MathHelper.sin(f) * h, e);
                }
            }
        }

        private void conjureBerserkers(double x, double z, double y) {
            BlockPos blockPos = new BlockPos(x, y, z);
            var world =  BlackBeardEntity.this.world;
            if (world instanceof ServerWorld sw) {
                BerserkerEntity berserkerEntity = InfiniumEntityType.BERSERKER.create(BlackBeardEntity.this.world);
                assert berserkerEntity != null;
                berserkerEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                berserkerEntity.initialize(sw, BlackBeardEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                sw.spawnEntityAndPassengers(berserkerEntity);
            }

        }

        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }
        protected Spell getSpell() {
            return Spell.FANGS;
        }
    }

}
