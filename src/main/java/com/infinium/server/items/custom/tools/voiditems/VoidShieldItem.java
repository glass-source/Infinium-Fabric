package com.infinium.server.items.custom.tools.voiditems;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class VoidShieldItem extends FabricShieldItem implements InfiniumItem {

    public VoidShieldItem(Settings settings, int cooldownTicks, int enchantability, Item... repairItems) {
        super(settings, cooldownTicks, enchantability, repairItems);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
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
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendInfiniumToolTip(tooltip, "void", 2);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
