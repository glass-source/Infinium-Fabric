package com.infinium.server.items.custom;

import com.infinium.server.items.InfiniumItems;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;

public interface InfiniumItem {

    default void enchantMagmaTool(ItemStack stack) {
        var item = stack.getItem();
        stack.addEnchantment(Enchantments.MENDING, 1);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.LOOTING, 10);

        if (item.equals(InfiniumItems.MAGMA_SWORD)) {
            stack.addEnchantment(Enchantments.KNOCKBACK, 1);
            stack.addEnchantment(Enchantments.SHARPNESS, 10);
            stack.addEnchantment(Enchantments.SMITE, 10);
            stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 10);
            stack.addEnchantment(Enchantments.SWEEPING, 10);

        } else if (item.equals(InfiniumItems.MAGMA_AXE)) {
            stack.addEnchantment(Enchantments.EFFICIENCY, 10);
            stack.addEnchantment(Enchantments.SHARPNESS, 10);
            stack.addEnchantment(Enchantments.SMITE, 10);
            stack.addEnchantment(Enchantments.BANE_OF_ARTHROPODS, 10);
            stack.addEnchantment(Enchantments.SWEEPING, 10);

        } else if (item.equals(InfiniumItems.MAGMA_BOW)) {
            stack.addEnchantment(Enchantments.INFINITY, 1);
            stack.addEnchantment(Enchantments.POWER, 10);
            stack.addEnchantment(Enchantments.PUNCH, 5);
            stack.addEnchantment(Enchantments.FLAME, 10);
        } else {
            stack.addEnchantment(Enchantments.EFFICIENCY, 10);
        }


    }

}
