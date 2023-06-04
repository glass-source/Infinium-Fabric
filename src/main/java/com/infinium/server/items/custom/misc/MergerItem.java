package com.infinium.server.items.custom.misc;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MergerItem extends Item implements InfiniumItem {
    private final Enchantment enchantment;
    private final int level;
    public MergerItem(Settings settings, Enchantment enchantment, int level) {
        super(settings);
        this.enchantment = enchantment;
        this.level = level;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        if (user.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(this)) {
            var offItem = user.getEquippedStack(EquipmentSlot.OFFHAND);

            if (!offItem.getItem().equals(Items.AIR)) {
                var enchantments = offItem.getEnchantments();
                var enchant = EnchantmentHelper.createNbt(EnchantmentHelper.getEnchantmentId(enchantment), (byte)level);
                boolean[] shouldUse = {true};

                enchantments.forEach(nbtElement -> {
                    if (nbtElement.equals(enchant)) {
                        shouldUse[0] = false;
                    }
                });

                if (!shouldUse[0]) {
                    finishUsing(user.getEquippedStack(EquipmentSlot.MAINHAND), world, user);
                    return super.use(world, user, hand);

                } else {
                    enchantments.forEach(nbtElement -> {
                        if (nbtElement.equals(enchant)) {
                            enchantments.remove(nbtElement);
                        }
                    });
                    offItem.addEnchantment(enchantment, level);
                    user.getEquippedStack(EquipmentSlot.MAINHAND).decrement(1);
                    world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.PLAYERS, 1, 0.03F);
                    finishUsing(user.getEquippedStack(EquipmentSlot.MAINHAND), world, user);
                }
            }
        }


        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendGeneralToolTip(stack, tooltip, 2);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
