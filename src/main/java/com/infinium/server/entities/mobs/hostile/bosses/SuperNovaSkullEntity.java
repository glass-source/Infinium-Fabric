package com.infinium.server.entities.mobs.hostile.bosses;

import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class SuperNovaSkullEntity extends ExplosiveProjectileEntity {

    private static final TrackedData<Boolean> CHARGED;
    public SuperNovaSkullEntity(World world, LivingEntity owner, double directionX, double directionY, double directionZ) {
        super(EntityType.WITHER_SKULL, owner, directionX, directionY, directionZ, world);
    }

    protected float getDrag() {
        return super.getDrag();
    }

    public boolean isOnFire() {
        return false;
    }

    public float getEffectiveExplosionResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState, float max) {
        return this.isCharged() && WitherEntity.canDestroy(blockState) ? Math.min(0.8F, max) : max;
    }

    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);

        if (!this.world.isClient) {

            Entity hitEntity = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            boolean wasHit;

            if (owner instanceof LivingEntity livingEntity) {

                wasHit = hitEntity.damage(DamageSource.WITHER, 8.0F);

                if (wasHit) {
                    if (hitEntity.isAlive()) {
                        this.applyDamageEffects(livingEntity, hitEntity);
                        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * 40, 1), this.getEffectCause());
                        livingEntity.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 20 * 40, 1), this.getEffectCause());

                    } else {
                        livingEntity.heal(5.0F);
                    }
                }
            }
        }
    }

    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.world.isClient) {
            this.world.createExplosion(this, this.getX(), this.getY(), this.getZ(), 3.0F, false, Explosion.DestructionType.BREAK);
            this.discard();
        }
    }
    public boolean collides() {
        return false;
    }

    public boolean damage(DamageSource source, float amount) {
        return false;
    }

    protected void initDataTracker() {
        this.dataTracker.startTracking(CHARGED, false);
    }

    public boolean isCharged() {
        return this.dataTracker.get(CHARGED);
    }

    public void setCharged(boolean charged) {
        this.dataTracker.set(CHARGED, charged);
    }

    protected boolean isBurning() {
        return false;
    }

    static {
        CHARGED = DataTracker.registerData(SuperNovaSkullEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }
}
