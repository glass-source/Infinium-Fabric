package com.infinium.global.mixin.server.entity;

import com.infinium.global.utils.DateUtils;
import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
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

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void removeLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci){
        var day = DateUtils.getDay();

        if (this.getType().equals(EntityType.IRON_GOLEM)) {
            if (day >= 7) {
                ci.cancel();
            }
        }
    }


}
