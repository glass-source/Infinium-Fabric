package com.infinium.server.items.custom.food;

import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.sanity.SanityManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class SanityItem extends Item {

    public SanityItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && user instanceof ServerPlayerEntity pl) {
            pl.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 240, 3));
            pl.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 240, 3));
            pl.removeStatusEffect(InfiniumEffects.MADNESS);
            Infinium.getInstance().getCore().getSanityManager().addSanity(pl, 40);
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }
}
