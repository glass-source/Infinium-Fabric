package com.infinium.mixin;

import com.infinium.api.effects.InfiniumEffects;
import com.infinium.api.items.global.InfiniumItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow public abstract ItemStack getOffHandStack();

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow public abstract void endCombat();

    @Shadow public abstract void readCustomDataFromNbt(NbtCompound nbt);

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source);

    @Shadow public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow @Nullable public abstract StatusEffectInstance removeStatusEffectInternal(@Nullable StatusEffect type);

    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Shadow public abstract boolean canTakeDamage();

    @Shadow @Final private DamageTracker damageTracker;

    @Shadow public abstract boolean damage(DamageSource source, float amount);

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {
            cir.setReturnValue(false);
            cir.cancel();

        } else if (this.hasStatusEffect(InfiniumEffects.MADNESS)) {
            var extraDamage = (2.5 * (this.getStatusEffect(InfiniumEffects.MADNESS).getAmplifier() + 1));
        }
    }

    @Inject(method = "applyDamage", at = @At("HEAD"), cancellable = true)
    public void onDamage(DamageSource source, float amount, CallbackInfo ci) {
        if(this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {
            ci.cancel();
        }
        if(this.hasStatusEffect(InfiniumEffects.MADNESS)) {
            amount *= (2.5 * (this.getStatusEffect(InfiniumEffects.MADNESS).getAmplifier()+1));

        }
    }

    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useVoidTotem(DamageSource source, CallbackInfoReturnable<Boolean> callback) {
        ItemStack mainHand = this.getMainHandStack();
        ItemStack offHand = this.getOffHandStack();

        if (offHand.getItem() == InfiniumItems.VOID_TOTEM || mainHand.getItem() == InfiniumItems.VOID_TOTEM) {

            if (offHand.getItem() == InfiniumItems.VOID_TOTEM) {
                offHand.decrement(1);
            } else {
                mainHand.decrement(1);
            }

            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 125, 3));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 7));
            this.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 120, 0));
            this.world.sendEntityStatus(this, (byte) 35);
            callback.setReturnValue(true);
        }


    }
}
