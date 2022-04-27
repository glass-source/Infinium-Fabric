package com.infinium.items.custom.armor;

import com.infinium.items.materials.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
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
                        double baseValue = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).getBaseValue();
                        if (baseValue + 10 < 30) {
                            p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(baseValue + 10);
                        } else {
                            p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(baseValue);

                        }
                        StatusEffectInstance[] effects = {
                          new StatusEffectInstance(StatusEffects.RESISTANCE, 120, 1),
                          new StatusEffectInstance(StatusEffects.STRENGTH, 120, 1),
                          new StatusEffectInstance(StatusEffects.SPEED, 120, 1),
                          new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 120, 0)
                        };
                        Arrays.stream(effects).toList().forEach(p::addStatusEffect);
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

        return boots == ModArmorMaterials.MAGMA && leggings == ModArmorMaterials.MAGMA && chestplate == ModArmorMaterials.MAGMA && helmet == ModArmorMaterials.MAGMA;
    }

}
