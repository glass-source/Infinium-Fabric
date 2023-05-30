package com.infinium.server.items.custom.food;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class NetheriteCarrotItem extends Item implements InfiniumItem {

    public NetheriteCarrotItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.SATURATION, 240, 1));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 240, 3));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 4800, 5));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 4800, 1));
        }
        return super.finishUsing(stack, world, user);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }



}
