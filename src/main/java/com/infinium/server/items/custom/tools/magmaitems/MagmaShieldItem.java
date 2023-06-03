package com.infinium.server.items.custom.tools.magmaitems;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagmaShieldItem extends FabricShieldItem implements InfiniumItem {

    public MagmaShieldItem(Settings settings, int cooldownTicks, int enchantability, Item... repairItems) {
        super(settings, cooldownTicks, enchantability, repairItems);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return fromMagmaToolHit(stack, target, attacker);
    }
    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        stack.addEnchantment(Enchantments.UNBREAKING, 10);
        stack.addEnchantment(Enchantments.MENDING, 1);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendInfiniumToolTip(tooltip, "magma", 2);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
