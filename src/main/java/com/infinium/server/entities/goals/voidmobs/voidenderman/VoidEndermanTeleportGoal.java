package com.infinium.server.entities.goals.voidmobs.voidenderman;


import com.infinium.server.entities.mobs.hostile.voidmobs.VoidEndermanEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class VoidEndermanTeleportGoal extends ActiveTargetGoal<PlayerEntity> {
        private final VoidEndermanEntity VoidEndermanEntity;
        @Nullable private PlayerEntity targetPlayer;
        private int lookAtPlayerWarmup;
        private int ticksSinceUnseenTeleport;
        private final TargetPredicate staringPlayerPredicate;
        private final TargetPredicate validTargetPredicate = TargetPredicate.createAttackable().ignoreVisibility();

        public VoidEndermanTeleportGoal(VoidEndermanEntity enderman, @Nullable Predicate<LivingEntity> targetPredicate) {
            super(enderman, PlayerEntity.class, 10, false, false, targetPredicate);
            this.VoidEndermanEntity = enderman;
            this.staringPlayerPredicate = TargetPredicate.createAttackable().setBaseMaxDistance(this.getFollowRange()).setPredicate((playerEntity) -> enderman.isPlayerStaring((PlayerEntity)playerEntity));
        }

        public boolean canStart() {
            this.targetPlayer = this.VoidEndermanEntity.world.getClosestPlayer(this.staringPlayerPredicate, this.VoidEndermanEntity);
            return this.targetPlayer != null;
        }

        public void start() {
            this.lookAtPlayerWarmup = this.getTickCount(5);
            this.ticksSinceUnseenTeleport = 0;
            this.VoidEndermanEntity.setProvoked();
        }

        public void stop() {
            this.targetPlayer = null;
            super.stop();
        }

        public boolean shouldContinue() {
            if (this.targetPlayer != null) {
                if (!this.VoidEndermanEntity.isPlayerStaring(this.targetPlayer)) {
                    return false;
                } else {
                    this.VoidEndermanEntity.lookAtEntity(this.targetPlayer, 10.0F, 10.0F);
                    return true;
                }
            } else {
                return this.targetEntity != null && this.validTargetPredicate.test(this.VoidEndermanEntity, this.targetEntity) || super.shouldContinue();
            }
        }

        public void tick() {
            if (this.VoidEndermanEntity.getTarget() == null) {
                super.setTargetEntity(null);
            }

            if (this.targetPlayer != null) {
                if (--this.lookAtPlayerWarmup <= 0) {
                    this.targetEntity = this.targetPlayer;
                    this.targetPlayer = null;
                    super.start();
                }
            } else {
                if (this.targetEntity != null && !this.VoidEndermanEntity.hasVehicle()) {
                    if (this.VoidEndermanEntity.isPlayerStaring((PlayerEntity)this.targetEntity)) {
                        if (this.targetEntity.squaredDistanceTo(this.VoidEndermanEntity) < 16.0) {
                            this.VoidEndermanEntity.teleportRandomly();
                        }

                        this.ticksSinceUnseenTeleport = 0;
                    } else if (this.targetEntity.squaredDistanceTo(this.VoidEndermanEntity) > 256.0 && this.ticksSinceUnseenTeleport++ >= this.getTickCount(30)) {
                        this.ticksSinceUnseenTeleport = 0;
                        this.VoidEndermanEntity.teleportTo(this.targetEntity.getX(), this.targetEntity.getY(), this.targetEntity.getY());
                    }
                }

                super.tick();
            }

        }
    }