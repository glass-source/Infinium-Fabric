package com.infinium.server.entities.mobs.hostile.nightmare;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.Random;


public class NightmareGhastEntity extends FlyingEntity implements Monster, InfiniumEntity {

    private static final TrackedData<Boolean> SHOOTING = DataTracker.registerData(NightmareGhastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public NightmareGhastEntity(EntityType<? extends NightmareGhastEntity> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = new Random().nextInt(120) + 20;
        this.moveControl = new NightmareGhastMoveControl(this);
        this.setCustomName(ChatFormatter.text("&cNightmare Ghast"));
    }

    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
    @Override
    public void initGoals() {
        super.initGoals();
        this.goalSelector.add(10, new FlyRandomlyGoal(this));
        this.goalSelector.add(3, new LookAtTargetGoal(this));
        this.goalSelector.add(3, new ShootFireballGoal(this));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SHOOTING, false);
    }

    @Override
    public double getAttributeValue(EntityAttribute attribute) {
        return super.getAttributeValue(attribute);
    }

    public static DefaultAttributeContainer.Builder createNightmareGhastAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 100.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 200.0);
    }

    public boolean isShooting() {
        return this.dataTracker.get(SHOOTING);
    }

    public void setShooting(boolean shooting) {
        this.dataTracker.set(SHOOTING, shooting);
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_GHAST_AMBIENT;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_GHAST_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_GHAST_DEATH;
    }

    public static class NightmareGhastMoveControl extends MoveControl {
        private final NightmareGhastEntity ghast;
        private int collisionCheckCooldown;

        public NightmareGhastMoveControl(NightmareGhastEntity ghast) {
            super(ghast);
            this.ghast = ghast;
        }

        public void tick() {
            if (this.state == State.MOVE_TO) {
                if (this.collisionCheckCooldown-- <= 0) {
                    this.collisionCheckCooldown += this.ghast.getRandom().nextInt(5) + 2;
                    Vec3d vec3d = new Vec3d(this.targetX - this.ghast.getX(), this.targetY - this.ghast.getY(), this.targetZ - this.ghast.getZ());
                    double d = vec3d.length();
                    vec3d = vec3d.normalize();
                    if (this.willCollide(vec3d, MathHelper.ceil(d))) {
                        this.ghast.setVelocity(this.ghast.getVelocity().add(vec3d.multiply(0.1)));
                    } else {
                        this.state = State.WAIT;
                    }
                }

            }
        }

        private boolean willCollide(Vec3d direction, int steps) {
            Box box = this.ghast.getBoundingBox();

            for(int i = 1; i < steps; ++i) {
                box = box.offset(direction);
                if (!this.ghast.world.isSpaceEmpty(this.ghast, box)) {
                    return false;
                }
            }

            return true;
        }
    }

    public static class FlyRandomlyGoal extends Goal {
        private final NightmareGhastEntity ghast;

        public FlyRandomlyGoal(NightmareGhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Control.MOVE));
        }

        public boolean canStart() {
            MoveControl moveControl = this.ghast.getMoveControl();
            if (!moveControl.isMoving()) {
                return true;
            } else {
                double d = moveControl.getTargetX() - this.ghast.getX();
                double e = moveControl.getTargetY() - this.ghast.getY();
                double f = moveControl.getTargetZ() - this.ghast.getZ();
                double g = d * d + e * e + f * f;
                return g < 1.0 || g > 3600.0;
            }
        }

        public boolean shouldContinue() {
            return false;
        }

        public void start() {
            Random random = this.ghast.getRandom();
            double d = this.ghast.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double e = this.ghast.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            double f = this.ghast.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
            this.ghast.getMoveControl().moveTo(d, e, f, 1.0);
        }
    }

    public static class LookAtTargetGoal extends Goal {
        private final NightmareGhastEntity ghast;

        public LookAtTargetGoal(NightmareGhastEntity ghast) {
            this.ghast = ghast;
            this.setControls(EnumSet.of(Control.LOOK));
        }

        public boolean canStart() {
            return true;
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            if (this.ghast.getTarget() == null) {
                Vec3d vec3d = this.ghast.getVelocity();
                this.ghast.setYaw(-((float)MathHelper.atan2(vec3d.y, vec3d.x)) * 57.295776F);
                this.ghast.bodyYaw = this.ghast.getYaw();
            } else {
                LivingEntity livingEntity = this.ghast.getTarget();
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0) {
                    double e = livingEntity.getX() - this.ghast.getX();
                    double f = livingEntity.getZ() - this.ghast.getZ();
                    this.ghast.setYaw(-((float)MathHelper.atan2(e, f)) * 57.295776F);
                    this.ghast.bodyYaw = this.ghast.getYaw();
                }
            }

        }
    }

    public static class ShootFireballGoal extends Goal {
        private final NightmareGhastEntity ghast;
        public int cooldown;

        public ShootFireballGoal(NightmareGhastEntity ghast) {
            this.ghast = ghast;
        }

        public boolean canStart() {
            return this.ghast.getTarget() != null;
        }

        public void start() {
            this.cooldown = 0;
        }

        public void stop() {
            this.ghast.setShooting(false);
        }

        public boolean shouldRunEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity livingEntity = this.ghast.getTarget();
            if (livingEntity != null) {
                if (livingEntity.squaredDistanceTo(this.ghast) < 4096.0 && this.ghast.canSee(livingEntity)) {
                    World world = this.ghast.world;
                    ++this.cooldown;
                    if (this.cooldown == 10 && !this.ghast.isSilent()) {
                        world.syncWorldEvent(null, 1015, this.ghast.getBlockPos(), 0);
                    }

                    if (this.cooldown == 20) {
                        Vec3d vec3d = this.ghast.getRotationVec(1.0F);
                        double f = livingEntity.getX() - (this.ghast.getX() + vec3d.x * 4.0);
                        double g = livingEntity.getBodyY(0.5) - (0.5 + this.ghast.getBodyY(0.5));
                        double h = livingEntity.getZ() - (this.ghast.getZ() + vec3d.z * 4.0);
                        if (!this.ghast.isSilent()) {
                            world.syncWorldEvent(null, 1016, this.ghast.getBlockPos(), 0);
                        }

                        FireballEntity fireballEntity = new FireballEntity(world, this.ghast, f, g, h, 4);
                        fireballEntity.setPosition(this.ghast.getX() + vec3d.x * 4.0, this.ghast.getBodyY(0.5) + 0.5, fireballEntity.getZ() + vec3d.z * 4.0);
                        world.spawnEntity(fireballEntity);
                        this.cooldown = -40;
                    }
                } else if (this.cooldown > 0) {
                    --this.cooldown;
                }

                this.ghast.setShooting(this.cooldown > 10);
            }
        }
    }
}
