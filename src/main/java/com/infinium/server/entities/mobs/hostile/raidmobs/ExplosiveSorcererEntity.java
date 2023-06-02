package com.infinium.server.entities.mobs.hostile.raidmobs;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import com.infinium.server.items.InfiniumItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

public class ExplosiveSorcererEntity extends SpellcastingIllagerEntity implements InfiniumEntity {
    public ExplosiveSorcererEntity(EntityType<? extends ExplosiveSorcererEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.text("&6Explosive Sorcerer"));
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setTransBanner(world, this);
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public static DefaultAttributeContainer.Builder createSorcererAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.6)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0);
    }

    @Override
    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        if (this.random.nextBoolean()) this.dropStack(new ItemStack(InfiniumItems.VOID_TOTEM));
    }

    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(2, new FleeEntityGoal<>(this, PlayerEntity.class, 8.0F, 0.6, 1.0));
        this.goalSelector.add(4, new SummonVexGoal());
        this.goalSelector.add(5, new ConjureFangsGoal());
        this.goalSelector.add(8, new WanderAroundGoal(this, 0.6));
        this.goalSelector.add(1, new LookAtTargetGoal());
        this.goalSelector.add(9, new LookAtEntityGoal(this, PlayerEntity.class, 3.0F, 1.0F));
        this.goalSelector.add(10, new LookAtEntityGoal(this, MobEntity.class, 8.0F));
        this.targetSelector.add(1, (new RevengeGoal(this, RaiderEntity.class)).setGroupRevenge());
        this.targetSelector.add(2, (new ActiveTargetGoal<>(this, PlayerEntity.class, true)).setMaxTimeWithoutVisibility(300));
    }

    protected void initDataTracker() {
        super.initDataTracker();
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public SoundEvent getCelebratingSound() {
        return SoundEvents.ENTITY_EVOKER_CELEBRATE;
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    protected void mobTick() {
        super.mobTick();
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
        } else {
            return false;
        }
    }
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOKER_AMBIENT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_EVOKER_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_EVOKER_HURT;
    }

    protected SoundEvent getCastSpellSound() {
        return SoundEvents.ENTITY_EVOKER_CAST_SPELL;
    }

    public void addBonusForWave(int wave, boolean unused) {
    }
    private class SummonVexGoal extends SpellcastingIllagerEntity.CastSpellGoal {
        private final TargetPredicate closeVexPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0).ignoreVisibility().ignoreDistanceScalingFactor();

        SummonVexGoal() {
            super();
        }

        public boolean canStart() {
            if (!super.canStart()) {
                return false;
            } else {
                int i = ExplosiveSorcererEntity.this.world.getTargets(VexEntity.class, this.closeVexPredicate, ExplosiveSorcererEntity.this, ExplosiveSorcererEntity.this.getBoundingBox().expand(16.0)).size();
                return ExplosiveSorcererEntity.this.random.nextInt(8) + 1 > i;
            }
        }

        protected int getSpellTicks() {
            return 100;
        }

        protected int startTimeDelay() {
            return 340;
        }

        protected void castSpell() {
            ServerWorld serverWorld = (ServerWorld)ExplosiveSorcererEntity.this.world;

            for(int i = 0; i < 3; ++i) {
                BlockPos blockPos = ExplosiveSorcererEntity.this.getBlockPos().add(-2 + ExplosiveSorcererEntity.this.random.nextInt(5), 1, -2 + ExplosiveSorcererEntity.this.random.nextInt(5));
                VexEntity vexEntity = EntityType.VEX.create(ExplosiveSorcererEntity.this.world);
                assert vexEntity != null;
                vexEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                vexEntity.initialize(serverWorld, ExplosiveSorcererEntity.this.world.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, null, null);
                vexEntity.setOwner(ExplosiveSorcererEntity.this);
                vexEntity.setBounds(blockPos);
                vexEntity.setLifeTicks(20 * (30 + ExplosiveSorcererEntity.this.random.nextInt(90)));
                serverWorld.spawnEntityAndPassengers(vexEntity);
                setVexAttributes(vexEntity);
            }

        }

        public void setVexAttributes(VexEntity vexEntity) {
            var health = vexEntity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            var itemStack = new ItemStack(Items.NETHERITE_SWORD);
            vexEntity.equipStack(EquipmentSlot.MAINHAND, itemStack);
            vexEntity.setCustomName(ChatFormatter.text("&cSpiritual Entity"));
            if (health != null) {
                health.setBaseValue(40.0f);
                vexEntity.setHealth(40.0f);
                vexEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 0));
            }
        }

        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON;
        }

        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.SUMMON_VEX;
        }
    }

    private class ConjureFangsGoal extends SpellcastingIllagerEntity.CastSpellGoal {
        ConjureFangsGoal() {
            super();
        }
        protected int getSpellTicks() {
            return 40;
        }
        protected int startTimeDelay() {
            return 100;
        }
        protected void castSpell() {
            LivingEntity livingEntity = ExplosiveSorcererEntity.this.getTarget();
            assert livingEntity != null;
            double d = Math.min(livingEntity.getY(), ExplosiveSorcererEntity.this.getY());
            double e = Math.max(livingEntity.getY(), ExplosiveSorcererEntity.this.getY()) + 1.0;
            float f = (float)MathHelper.atan2(livingEntity.getZ() - ExplosiveSorcererEntity.this.getZ(), livingEntity.getX() - ExplosiveSorcererEntity.this.getX());
            int i;
            if (ExplosiveSorcererEntity.this.squaredDistanceTo(livingEntity) < 9.0) {
                float g;
                for(i = 12; i > 0; --i) {
                    g = f + (float)i * 3.14f * 0.4F;
                    this.conjureFangs(ExplosiveSorcererEntity.this.getX() + (double)MathHelper.cos(g) * 1.5, ExplosiveSorcererEntity.this.getZ() + (double)MathHelper.sin(g) * 1.5, d, e, g, 0);
                }

                for(i = 0; i < 8; ++i) {
                    g = f + (float)i * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                    this.conjureFangs(ExplosiveSorcererEntity.this.getX() + (double)MathHelper.cos(g) * 2.5, ExplosiveSorcererEntity.this.getZ() + (double)MathHelper.sin(g) * 2.5, d, e, g, 3);
                }
            } else {
                for(i = 0; i < 16; ++i) {
                    double h = 1.25 * (double)(i + 1);
                    this.conjureFangs(ExplosiveSorcererEntity.this.getX() + (double)MathHelper.cos(f) * h, ExplosiveSorcererEntity.this.getZ() + (double)MathHelper.sin(f) * h, d, e, f, i);
                }
            }

        }

        private void conjureFangs(double x, double z, double maxY, double y, float yaw, int warmup) {
            BlockPos blockPos = new BlockPos(x, y, z);
            boolean bl = false;
            double d = 0.0;

            do {
                BlockPos blockPos2 = blockPos.down();
                BlockState blockState = ExplosiveSorcererEntity.this.world.getBlockState(blockPos2);
                if (blockState.isSideSolidFullSquare(ExplosiveSorcererEntity.this.world, blockPos2, Direction.UP)) {
                    if (!ExplosiveSorcererEntity.this.world.isAir(blockPos)) {
                        BlockState blockState2 = ExplosiveSorcererEntity.this.world.getBlockState(blockPos);
                        VoxelShape voxelShape = blockState2.getCollisionShape(ExplosiveSorcererEntity.this.world, blockPos);
                        if (!voxelShape.isEmpty()) {
                            d = voxelShape.getMax(Direction.Axis.Y);
                        }
                    }

                    bl = true;
                    break;
                }

                blockPos = blockPos.down();
            } while(blockPos.getY() >= MathHelper.floor(maxY) - 1);

            if (bl) {
                if (world.random.nextBoolean()) world.createExplosion(ExplosiveSorcererEntity.this, x, blockPos.getY() + d, z, 2.0f, false, Explosion.DestructionType.NONE);
                ExplosiveSorcererEntity.this.world.spawnEntity(new EvokerFangsEntity(ExplosiveSorcererEntity.this.world, x, (double)blockPos.getY() + d, z, yaw, warmup, ExplosiveSorcererEntity.this));
            }
        }
        protected SoundEvent getSoundPrepare() {
            return SoundEvents.ENTITY_EVOKER_PREPARE_ATTACK;
        }
        protected SpellcastingIllagerEntity.Spell getSpell() {
            return SpellcastingIllagerEntity.Spell.FANGS;
        }
    }
}
