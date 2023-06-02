package com.infinium.server.items.custom.tools.voiditems;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidShovelItem extends ShovelItem implements InfiniumItem {

    public VoidShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            stack.damage(1, attacker, p -> p.sendToolBreakStatus(attacker.getActiveHand()));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 3));
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendVoidToolTip(stack, tooltip, 2);
        super.appendTooltip(stack, world, tooltip, context);
    }

}
