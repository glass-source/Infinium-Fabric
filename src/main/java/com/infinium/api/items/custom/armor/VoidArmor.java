package com.infinium.api.items.custom.armor;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class VoidArmor extends ArmorItem {

    public VoidArmor(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {

        if (!world.isClient()) {
            if (entity instanceof PlayerEntity p) {
                if (hasFullArmor(p)) {

                    if (hasVoidArmor(p)) {
                        StatusEffect[] effects = {
                                StatusEffects.RESISTANCE,
                                StatusEffects.SPEED,
                        };
                        Arrays.stream(effects).toList().forEach(status -> {
                            if (!p.hasStatusEffect(status)) p.addStatusEffect(new StatusEffectInstance(status, 140, 0));
                            if (p.getStatusEffect(status).getDuration() < 60) p.addStatusEffect(new StatusEffectInstance(status, 140, 0));
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

    private boolean hasVoidArmor(PlayerEntity user) {
        var inventory = user.getInventory();
        var boots = ((ArmorItem) inventory.getArmorStack(0).getItem()).getMaterial();
        var leggings = ((ArmorItem) inventory.getArmorStack(1).getItem()).getMaterial();
        var chestplate = ((ArmorItem) inventory.getArmorStack(2).getItem()).getMaterial();
        var helmet = ((ArmorItem) inventory.getArmorStack(3).getItem()).getMaterial();

        return boots == InfiniumArmorMaterials.VOID && leggings == InfiniumArmorMaterials.VOID && chestplate == InfiniumArmorMaterials.VOID && helmet == InfiniumArmorMaterials.VOID;
    }


}