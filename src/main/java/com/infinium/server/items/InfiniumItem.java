package com.infinium.server.items;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public interface InfiniumItem {

    default void appendInfiniumToolTip(List<Text> tooltip, String toolString, int lines) {
        if (Screen.hasShiftDown()) {
            for (int i = 0; i < lines; i++) {
                tooltip.add(new TranslatableText("item.infinium." + toolString + "_tool.tooltip.shift" + i));
            }
        } else {
            tooltip.add(new TranslatableText("item.infinium.general_" + toolString + ".tooltip"));
        }
    }

    default void appendGeneralToolTip(ItemStack stack, List<Text> tooltip, int lines) {
        if (Screen.hasShiftDown()) {
            for (int i = 0; i < lines; i++) {
                tooltip.add(new TranslatableText("item.infinium." + stack.getItem() + ".tooltip.shift" + i));
            }
        } else {
            tooltip.add(new TranslatableText("item.infinium.general.tooltip"));
        }
    }

    default boolean fromMagmaToolHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            stack.damage(1, attacker, p -> p.sendToolBreakStatus(attacker.getActiveHand()));
            if (target instanceof EndermanEntity || target instanceof ShulkerEntity) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 4));
            } else {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 4));
            }
            return true;
        }
        return false;
    }

    default void enchantMagmaTools(ItemStack stack) {
        stack.addEnchantment(Enchantments.MENDING, 1);
        stack.addEnchantment(Enchantments.EFFICIENCY, 10);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
    }

    default void enchantMagmaSword(ItemStack stack) {
        stack.addEnchantment(Enchantments.MENDING, 1);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        stack.addEnchantment(Enchantments.LOOTING, 10);
        stack.addEnchantment(Enchantments.KNOCKBACK, 1);
        stack.addEnchantment(Enchantments.SHARPNESS, 10);
        stack.addEnchantment(Enchantments.SMITE, 10);
        stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 10);
        stack.addEnchantment(Enchantments.SWEEPING, 10);
    }

    default void enchantMagmaBow(ItemStack stack) {
        stack.addEnchantment(Enchantments.MENDING, 1);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        stack.addEnchantment(Enchantments.LOOTING, 10);
        stack.addEnchantment(Enchantments.INFINITY, 1);
        stack.addEnchantment(Enchantments.POWER, 10);
        stack.addEnchantment(Enchantments.PUNCH, 5);
        stack.addEnchantment(Enchantments.FLAME, 10);
    }

}
