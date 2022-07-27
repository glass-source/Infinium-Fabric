package com.infinium.global.items.materials;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Lazy;

import java.util.function.Supplier;

public enum InfiniumArmorMaterials implements ArmorMaterial {

    MAGMA("magma", 37, new int[]{3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 5.0F, 0.5F, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)),

    MAGMA_ELYTRA("magma_elytra", 37, new int[]{0, 0, 4, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, 5.0F, 0.0F, () -> Ingredient.ofItems(Items.PHANTOM_MEMBRANE)),

    VOID("void", 37, new int[]{3, 6, 8, 3}, 0, SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, 3.0F, 0.25F, () -> Ingredient.ofItems(Items.NETHERITE_INGOT)),

    VOID_ELYTRA("void_elytra", 37, new int[]{0, 0, 2, 0}, 0, SoundEvents.ITEM_ARMOR_EQUIP_ELYTRA, 3.0F, 0.0F, () -> Ingredient.ofItems(Items.PHANTOM_MEMBRANE));

    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantability;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Lazy<Ingredient> repairIngredientSupplier;

    InfiniumArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantability, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredientSupplier) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantability = enchantability;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredientSupplier = new Lazy<>(repairIngredientSupplier);
    }

    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * this.durabilityMultiplier;
    }

    public int getProtectionAmount(EquipmentSlot slot) {
        return this.protectionAmounts[slot.getEntitySlotId()];
    }

    public int getEnchantability() {
        return this.enchantability;
    }

    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    public Ingredient getRepairIngredient() {
        return this.repairIngredientSupplier.get();
    }

    public String getName() {
        return this.name;
    }

    public float getToughness() {
        return this.toughness;
    }

    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
