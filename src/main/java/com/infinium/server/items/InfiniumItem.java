package com.infinium.server.items;

import com.infinium.Infinium;
import com.infinium.global.utils.EntityDataSaver;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.List;

public interface InfiniumItem {

    default int getCurrentCooldown(PlayerEntity user) {
        var data = ((EntityDataSaver) user).getPersistentData();
        var cooldownString = "infinium.cooldown." + this;
        return data.getInt(cooldownString) - Infinium.getInstance().getCore().getServer().getTicks();

    }

    default void appendVoidToolTip(ItemStack stack, List<Text> tooltip, int lines) {
        if (Screen.hasShiftDown()) {
            for (int i = 0; i < lines; i++) {
                tooltip.add(new TranslatableText("item.infinium.void_tool.tooltip.shift" + i));
            }
        } else {
            tooltip.add(new TranslatableText("item.infinium.general_void.tooltip"));
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
    default void enchantMagmaTools(ItemStack stack) {
        stack.addEnchantment(Enchantments.MENDING, 1);
        stack.addEnchantment(Enchantments.EFFICIENCY, 10);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        stack.addEnchantment(Enchantments.LOOTING, 10);
    }
    default void enchantMagmaSword(ItemStack stack) {
        stack.addEnchantment(Enchantments.EFFICIENCY, 10);
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
        stack.addEnchantment(Enchantments.EFFICIENCY, 10);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.FIRE_ASPECT, 10);
        stack.addEnchantment(Enchantments.LOOTING, 10);
        stack.addEnchantment(Enchantments.INFINITY, 1);
        stack.addEnchantment(Enchantments.POWER, 10);
        stack.addEnchantment(Enchantments.PUNCH, 5);
        stack.addEnchantment(Enchantments.FLAME, 10);
    }

}
