package com.infinium.mixin;

import com.infinium.api.items.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
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

    @Shadow public abstract ItemStack getMainHandStack();

    @Shadow public abstract ItemStack getOffHandStack();

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract boolean clearStatusEffects();

    @Shadow public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Inject(at = @At("HEAD"), method = "tryUseTotem")
    public void useVoidTotem(DamageSource source, CallbackInfoReturnable<Boolean> callback) {

        ItemStack mainHand = this.getMainHandStack();
        ItemStack offHand = this.getOffHandStack();

        if (offHand.getItem() == ModItems.VOID_TOTEM || mainHand.getItem() == ModItems.VOID_TOTEM) {

            if (offHand.getItem() == ModItems.VOID_TOTEM) {
                offHand.decrement(1);
            } else {
                mainHand.decrement(1);
            }

            this.setHealth(1.0F);
            this.clearStatusEffects();
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 125, 3));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 350, 7));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 240, 4));
            this.world.sendEntityStatus(this, (byte) 35);
        }

    }
}
