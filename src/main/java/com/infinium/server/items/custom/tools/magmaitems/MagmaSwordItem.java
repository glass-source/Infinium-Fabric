package com.infinium.server.items.custom.tools.magmaitems;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.infinium.server.items.custom.tools.magmaitems.MagmaAxeItem.fromHit;

public class MagmaSwordItem extends SwordItem implements InfiniumItem {

    public MagmaSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return fromHit(stack, target, attacker);
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

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        enchantMagmaSword(stack);
    }
}
