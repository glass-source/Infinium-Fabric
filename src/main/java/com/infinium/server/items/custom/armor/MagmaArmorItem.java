package com.infinium.server.items.custom.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.infinium.server.items.InfiniumItems;
import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.MendingEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class MagmaArmorItem extends ArmorItem implements ItemConvertible, InfiniumItem {

    public MagmaArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);

    }
    @Override
    public boolean isDamageable() {
        return super.isDamageable();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;
        if (!(entity instanceof PlayerEntity p)) return;
        if (!hasMagmaArmor(p)) return;

        StatusEffect[] effects = {
        StatusEffects.RESISTANCE,
        StatusEffects.SPEED,
        StatusEffects.FIRE_RESISTANCE};

        Arrays.stream(effects).toList().forEach(status -> {
            if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 1));
            if (p.getStatusEffect(status).getDuration() < 120)
                p.addStatusEffect(new StatusEffectInstance(status, 280, 1));
        });
    }

    private static boolean hasFullArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = inventory.getArmorStack(0);
        var leggings = inventory.getArmorStack(1);
        var chestplate = inventory.getArmorStack(2);
        var helmet = inventory.getArmorStack(3);
        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    public static boolean hasMagmaArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = inventory.getArmorStack(0).getItem();
        var leggings = inventory.getArmorStack(1).getItem();
        var chestplate = inventory.getArmorStack(2).getItem();
        var helmet = inventory.getArmorStack(3).getItem();

        return hasFullArmor(user) && (helmet instanceof MagmaArmorItem && chestplate instanceof MagmaArmorItem
        && leggings instanceof MagmaArmorItem && boots instanceof MagmaArmorItem);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        if (world.isClient) return;

        stack.addEnchantment(Enchantments.PROTECTION, 4);
        stack.addEnchantment(Enchantments.UNBREAKING, 4);
        stack.addEnchantment(Enchantments.MENDING, 0);

        super.onCraft(stack, world, player);
    }
}
