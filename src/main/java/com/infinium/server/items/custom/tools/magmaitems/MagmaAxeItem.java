package com.infinium.server.items.custom.tools.magmaitems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaAxeItem extends AxeItem {

    public MagmaAxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
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

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient) stack.damage(1, miner, p -> p.sendToolBreakStatus(miner.getActiveHand()));
        return true;
    }

    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

}
