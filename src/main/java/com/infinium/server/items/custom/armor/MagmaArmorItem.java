package com.infinium.server.items.custom.armor;

import com.infinium.server.items.InfiniumItems;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.UUID;

public class MagmaArmorItem extends ArmorItem implements InfiniumItem {

    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Magma Armor Healthboost", 24, EntityAttributeModifier.Operation.ADDITION);;
    public MagmaArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);

    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) return;
        if (!(entity instanceof PlayerEntity p)) return;
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;
        if (hasMagmaArmor(p)) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

            StatusEffect[] effects = {
            StatusEffects.RESISTANCE,
            StatusEffects.SPEED,
            StatusEffects.FIRE_RESISTANCE};

            Arrays.stream(effects).toList().forEach(status -> {
            if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 1));
            if (p.getStatusEffect(status).getDuration() < 120)
                p.addStatusEffect(new StatusEffectInstance(status, 280, 1));
            });
        } else {
            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }
        }
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
        super.onCraft(stack, world, player);

        stack.addEnchantment(Enchantments.PROTECTION, 8);
        stack.addEnchantment(Enchantments.UNBREAKING, 8);
        stack.addEnchantment(Enchantments.MENDING, 1);

        if (stack.getItem().equals(InfiniumItems.MAGMA_BOOTS)) {
            stack.addEnchantment(Enchantments.FEATHER_FALLING, 8);
            stack.addEnchantment(Enchantments.DEPTH_STRIDER, 8);
            stack.addEnchantment(Enchantments.SOUL_SPEED, 8);

        } else if (stack.getItem().equals(InfiniumItems.MAGMA_HELMET)) {
            stack.addEnchantment(Enchantments.AQUA_AFFINITY, 1);
            stack.addEnchantment(Enchantments.RESPIRATION, 8);
        }

    }
}
