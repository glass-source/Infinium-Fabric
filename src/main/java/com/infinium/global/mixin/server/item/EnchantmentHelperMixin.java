package com.infinium.global.mixin.server.item;

import com.google.common.collect.Lists;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "getPossibleEntries", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"), cancellable = true)
    private static void catchEnchantmentError(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        List<EnchantmentLevelEntry> list = Lists.newArrayList();
        boolean isBook = stack.isOf(Items.BOOK);
        for (Enchantment enchantment : Registry.ENCHANTMENT) {
            try {
                if (enchantment.isAcceptableItem(stack) || isBook) {
                    for (int level = enchantment.getMaxLevel(); level > enchantment.getMinLevel() - 1; --level) {
                        if (power >= enchantment.getMinPower(level) && power <= enchantment.getMaxPower(level)) {
                            list.add(new EnchantmentLevelEntry(enchantment, level));
                            break;
                        }
                    }
                }
            } catch (AbstractMethodError | Exception e) {

                System.err.println("[Infinium] Skipping broken enchantment: " +
                        Registry.ENCHANTMENT.getId(enchantment) + " - " + e.getClass().getSimpleName() + ": " + e.getMessage());
            }
        }

        cir.setReturnValue(list);
    }

}
