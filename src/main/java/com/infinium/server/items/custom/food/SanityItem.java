package com.infinium.server.items.custom.food;

import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SanityItem extends Item implements InfiniumItem {

    public SanityItem(Settings settings) {
        super(settings);
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient() && user instanceof ServerPlayerEntity pl) {
            var sanityManager = Infinium.getInstance().getCore().getSanityManager();
            pl.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 240, 3));
            pl.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 240, 3));
            pl.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 240, 3));
            pl.removeStatusEffect(InfiniumEffects.MADNESS);
            sanityManager.add(pl, 40, sanityManager.SANITY);
        }
        return super.finishUsing(stack, world, user);
    }
    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendGeneralToolTip(stack, tooltip, 2);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
