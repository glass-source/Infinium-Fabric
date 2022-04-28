package com.infinium.api.items.custom.misc;

import com.infinium.api.effects.InfiniumEffects;
import net.kyori.adventure.audience.Audience;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;

public class ImmunityRuneItem extends Item {



    public ImmunityRuneItem(Settings settings) {
        super(settings);


    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {

        if(clickType.equals(ClickType.RIGHT)) {
            player.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 800, 0));

            return true;
        }else if(clickType.equals(ClickType.LEFT)) {

            return true;
        }

        return false;
    }
}
