package com.infinium.global.mixin;

import com.infinium.global.effects.InfiniumEffectRegistry;
import com.infinium.api.events.players.PlayerUseTotemEvent;
import com.infinium.global.items.groups.InfiniumItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
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

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void applyImmunity(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(this.hasStatusEffect(InfiniumEffectRegistry.IMMUNITY)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    public void applyMadness(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        float extraDamage = amount;

        if (this.hasStatusEffect(InfiniumEffectRegistry.MADNESS)) {
            extraDamage *= (1.05 * (this.getStatusEffect(InfiniumEffectRegistry.MADNESS).getAmplifier() + 1));
            if (this.isAlive()) this.applyDamage(source, extraDamage);
            if (this.getHealth() <= 0) this.kill();
            cir.setReturnValue(false);
        }
    }



    @Inject(at = @At("HEAD"), method = "tryUseTotem", cancellable = true)
    public void useVoidTotem(DamageSource source, CallbackInfoReturnable<Boolean> callback) {
        var world = this.getWorld();
        ItemStack itemStack = null;
        Hand[] handValue = Hand.values();
        for (Hand hand : handValue) {
            ItemStack itemStack2 = this.getStackInHand(hand);
            if (itemStack2.isOf(Items.TOTEM_OF_UNDYING) || itemStack2.isOf(InfiniumItems.VOID_TOTEM) || itemStack2.isOf(InfiniumItems.MAGMA_TOTEM)) {
                itemStack = itemStack2.copy();
                itemStack2.decrement(1);
                break;
            }
        }

        if (itemStack != null) {
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING) && source.isOutOfWorld()) return;
            if (itemStack.isOf(InfiniumItems.MAGMA_TOTEM) && source.isOutOfWorld()) return;
            callback.setReturnValue(true);
            PlayerUseTotemEvent.EVENT.invoker().use((PlayerEntity) world.getEntityById(this.getId()), itemStack);
        }
    }
}
