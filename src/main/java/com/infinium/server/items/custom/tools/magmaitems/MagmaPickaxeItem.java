package com.infinium.server.items.custom.tools.magmaitems;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static com.infinium.server.items.custom.tools.magmaitems.MagmaAxeItem.fromHit;

public class MagmaPickaxeItem extends PickaxeItem implements InfiniumItem {

    public MagmaPickaxeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
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

}
