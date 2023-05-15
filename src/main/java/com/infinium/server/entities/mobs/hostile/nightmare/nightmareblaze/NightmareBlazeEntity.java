package com.infinium.server.entities.mobs.hostile.nightmare.nightmareblaze;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;

import java.util.EnumSet;

public class NightmareBlazeEntity extends BlazeEntity {
    public NightmareBlazeEntity(EntityType<? extends BlazeEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createNightmareBlazeAttributes() {
        return BlazeEntity.createBlazeAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 24.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 60.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.28)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 3.5)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0);
    }

    protected void initGoals() {
        this.goalSelector.add(4, new ShootFireballGoal(this));
        this.goalSelector.add(5, new GoToWalkTargetGoal(this, 1.0));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0, 0.0F));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, (new RevengeGoal(this)).setGroupRevenge());
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    private static class ShootFireballGoal extends Goal {
        private final NightmareBlazeEntity blaze;
        private int fireballsFired;
        private int fireballCooldown;
        private int targetNotVisibleTicks;

        public ShootFireballGoal(NightmareBlazeEntity blaze) {
            this.blaze = blaze;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public boolean canStart() {
            LivingEntity livingEntity = this.blaze.getTarget();
            return livingEntity != null && livingEntity.isAlive() && this.blaze.canTarget(livingEntity);
        }

        public void start() {
            this.fireballsFired = 0;
        }

        public void stop() {

            this.targetNotVisibleTicks = 0;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            --this.fireballCooldown;
            LivingEntity livingEntity = this.blaze.getTarget();
            if (livingEntity != null) {
                boolean bl = this.blaze.getVisibilityCache().canSee(livingEntity);
                if (bl) {
                    this.targetNotVisibleTicks = 0;
                } else {
                    ++this.targetNotVisibleTicks;
                }

                double d = this.blaze.squaredDistanceTo(livingEntity);
                if (d < 4.0) {
                    if (!bl) {
                        return;
                    }

                    if (this.fireballCooldown <= 0) {
                        this.fireballCooldown = 20;
                        this.blaze.tryAttack(livingEntity);
                    }

                    this.blaze.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                } else if (d < this.getFollowRange() * this.getFollowRange() && bl) {
                    double e = livingEntity.getX() - this.blaze.getX();
                    double f = livingEntity.getBodyY(0.5) - this.blaze.getBodyY(0.5);
                    double g = livingEntity.getZ() - this.blaze.getZ();
                    if (this.fireballCooldown <= 0) {
                        ++this.fireballsFired;
                        if (this.fireballsFired == 1) {
                            this.fireballCooldown = 60;
                        } else if (this.fireballsFired <= 4) {
                            this.fireballCooldown = 6;
                        } else {
                            this.fireballCooldown = 100;
                            this.fireballsFired = 0;
                        }

                        if (this.fireballsFired > 1) {
                            double h = Math.sqrt(Math.sqrt(d)) * 0.5;
                            if (!this.blaze.isSilent()) {
                                this.blaze.world.syncWorldEvent(null, WorldEvents.BLAZE_SHOOTS, this.blaze.getBlockPos(), 0);
                            }

                            for(int i = 0; i < 1; ++i) {
                                FireballEntity fireballEntity = new FireballEntity(this.blaze.world, this.blaze, e + this.blaze.getRandom().nextGaussian() * h, f, g + this.blaze.getRandom().nextGaussian() * h, 3);
                                fireballEntity.setPosition(fireballEntity.getX(), this.blaze.getBodyY(0.5) + 0.5, fireballEntity.getZ());
                                this.blaze.world.spawnEntity(fireballEntity);
                            }
                        }
                    }

                    this.blaze.getLookControl().lookAt(livingEntity, 10.0F, 10.0F);
                } else if (this.targetNotVisibleTicks < 5) {
                    this.blaze.getMoveControl().moveTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1.0);
                }

                super.tick();
            }
        }

        private double getFollowRange() {
            return this.blaze.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE);
        }
    }

}
