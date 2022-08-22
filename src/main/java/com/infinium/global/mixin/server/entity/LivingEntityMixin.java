package com.infinium.global.mixin.server.entity;

import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
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

    @Shadow protected abstract void applyDamage(DamageSource source, float amount);

    @Shadow public abstract boolean isAlive();

    @Shadow public abstract float getHealth();

    @Shadow public abstract void kill();

    @Shadow public abstract ItemStack getStackInHand(Hand hand);

    @Shadow protected abstract void playBlockFallSound();

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void applyImmunity(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {
            cir.setReturnValue(false);
            cir.cancel();

        }
    }


}
