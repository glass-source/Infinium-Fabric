package com.infinium.global.mixin.server.entity;

import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);
    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);
    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);
    @Shadow public abstract boolean damage(DamageSource source, float amount);
    @Shadow @Nullable public abstract LivingEntity getAttacker();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void applyImmunity(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }


    @ModifyVariable(method = "damage", at = @At("TAIL"), ordinal = 0, argsOnly = true)
    private float applyImmunityAndMadness(float amount) {
        if (this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {

            return 0;
        } else if (this.hasStatusEffect(InfiniumEffects.MADNESS)) {
            var level = this.getStatusEffect(InfiniumEffects.MADNESS).getAmplifier();
            return amount * (level + (level * 0.15f));
        } else {
            return amount;
        }
    }
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!source.isExplosive()) return;
        if (this.isPlayer()) return;
        if (Infinium.getInstance().getDateUtils() == null) return;
        var day = Infinium.getInstance().getDateUtils().getCurrentDay();;
        var entityTypeString = this.getType().toString();

        if (day >= 7) {
            switch (entityTypeString) {

                case  "entity.minecraft.pillager"
                    , "entity.minecraft.vindicator"
                    , "entity.minecraft.evoker" -> {
                    cir.setReturnValue(false);
                    cir.cancel();
                }
            }
        }

        if (day >= 14) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    private void onDeath(DamageSource source, CallbackInfo ci) {
        if (Infinium.getInstance().getDateUtils() == null) return;
        var day = Infinium.getInstance().getDateUtils().getCurrentDay();
        var world = this.getWorld();
        var blockPos = this.getBlockPos();

        if (day >= 14) {
            if (random.nextInt(100) == 1) createExplosionFromEntity(null, world, blockPos, 2.5f);

            switch (this.getType().toString()) {
                case "entity.minecraft.glow_squid"
                   , "entity.minecraft.squid"-> createExplosionFromEntity(null, world, blockPos, 40.0f, false);
            }

        }

    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void removeLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci){
        if (Infinium.getInstance().getDateUtils() == null) return;
        var day = Infinium.getInstance().getDateUtils().getCurrentDay();

        if (this.getType().equals(EntityType.IRON_GOLEM)) {
            if (day >= 7) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void solarEclipseRemoveLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci){
        if (Infinium.getInstance().getDateUtils() == null) return;
        var eclipseManager = Infinium.getInstance().getCore().getEclipseManager();
        if (!eclipseManager.isActive()) return;
        var day = Infinium.getInstance().getDateUtils().getCurrentDay();

        if (day >= 7 && day < 14) {

            switch  (this.getType().toString()) {

                case "entity.minecraft.wither_skeleton" -> {
                    if (random.nextInt(10) >= 6) {
                        var item = Items.WITHER_SKELETON_SKULL.getDefaultStack();
                        item.increment(1);
                        dropStack(item);
                    }

                }

                case "entity.infinium.ghoul_spider" -> {
                    var item = Items.FERMENTED_SPIDER_EYE.getDefaultStack();
                    item.increment(random.nextInt(6));
                    dropStack(item);
                }

                case  "entity.minecraft.pillager"
                    , "entity.minecraft.vindicator"
                    , "entity.minecraft.evoker" -> {

                    if (random.nextInt(10) >= 3) {
                        var item = Items.EMERALD_BLOCK.getDefaultStack();
                        item.increment(1 + random.nextInt(3));
                        dropStack(item);
                    }
                }
            }
        }

    }

    private void createExplosionFromEntity(@Nullable LivingEntity entity, World world, BlockPos position, float explosionPower) {
        world.createExplosion(entity, position.getX(), position.getY(), position.getZ(), explosionPower, Explosion.DestructionType.DESTROY);
    }

    private void createExplosionFromEntity(@Nullable LivingEntity entity, World world, BlockPos position, float explosionPower, boolean breakBlocks) {
        world.createExplosion(entity, position.getX(), position.getY(), position.getZ(), explosionPower,  breakBlocks ? Explosion.DestructionType.DESTROY : Explosion.DestructionType.NONE);
    }

}
