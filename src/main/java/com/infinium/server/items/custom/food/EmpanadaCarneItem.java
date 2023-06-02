package com.infinium.server.items.custom.food;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
public class EmpanadaCarneItem extends Item implements InfiniumItem {

    public EmpanadaCarneItem(Settings settings) {
        super(settings);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient()) {
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 160, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2000, 2));
            user.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 2000, 0));
        }
        return super.finishUsing(stack, world, user);
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }



}
