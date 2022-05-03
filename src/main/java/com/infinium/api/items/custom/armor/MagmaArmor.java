package com.infinium.api.items.custom.armor;

import com.infinium.api.items.materials.InfiniumArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Arrays;

public class MagmaArmor extends ArmorItem {

    public MagmaArmor(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (!world.isClient()) {
            if (entity instanceof PlayerEntity p) {
                if (hasFullArmor(p)) {

                    if (hasMagmaArmor(p)) {
                        StatusEffect[] effects = {
                                StatusEffects.RESISTANCE,
                                StatusEffects.SPEED,
                                StatusEffects.FIRE_RESISTANCE
                        };
                        Arrays.stream(effects).toList().forEach(status -> {
                            if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 280, 0));
                            if (p.getStatusEffect(status).getDuration() < 120) p.addStatusEffect(new StatusEffectInstance(status, 280, 0));
                        });
                    }
                }
            }
        }
    }

    private boolean hasFullArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = inventory.getArmorStack(0);
        var leggings = inventory.getArmorStack(1);
        var chestplate = inventory.getArmorStack(2);
        var helmet = inventory.getArmorStack(3);
        return !boots.isEmpty() && !leggings.isEmpty() && !chestplate.isEmpty() && !helmet.isEmpty();
    }

    private boolean hasMagmaArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = ((ArmorItem) inventory.getArmorStack(0).getItem()).getMaterial();
        var leggings = ((ArmorItem) inventory.getArmorStack(1).getItem()).getMaterial();
        var chestplate = ((ArmorItem) inventory.getArmorStack(2).getItem()).getMaterial();
        var helmet = ((ArmorItem) inventory.getArmorStack(3).getItem()).getMaterial();

        return boots == InfiniumArmorMaterials.MAGMA && leggings == InfiniumArmorMaterials.MAGMA && chestplate == InfiniumArmorMaterials.MAGMA && helmet == InfiniumArmorMaterials.MAGMA;
    }

}
