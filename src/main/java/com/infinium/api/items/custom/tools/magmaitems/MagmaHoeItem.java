package com.infinium.api.items.custom.tools.magmaitems;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaHoeItem extends HoeItem {

    public MagmaHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient()) {
            if (miner instanceof PlayerEntity p) {
                p.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 120, 1));
            }
        }
        return super.postMine(stack, world, state, pos, miner);
    }
}
