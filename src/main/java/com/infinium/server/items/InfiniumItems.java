package com.infinium.server.items;

import com.github.crimsondawn45.fabricshieldlib.lib.object.FabricShieldItem;
import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.custom.armor.MagmaArmorItem;
import com.infinium.server.items.custom.armor.MagmaElytraItem;
import com.infinium.server.items.custom.armor.VoidArmorItem;
import com.infinium.server.items.custom.armor.VoidElytraItem;
import com.infinium.server.items.custom.food.NetheriteCarrotItem;
import com.infinium.server.items.custom.food.SanityItem;
import com.infinium.server.items.custom.misc.InfiniumDiscItem;
import com.infinium.server.items.custom.misc.InfiniumTotemItem;
import com.infinium.server.items.custom.misc.MergerItem;
import com.infinium.server.items.custom.misc.VoidEyeItem;
import com.infinium.server.items.custom.tools.magmaitems.*;
import com.infinium.server.items.custom.tools.runes.*;
import com.infinium.server.items.custom.tools.utility.EnderWandItem;
import com.infinium.server.items.custom.tools.utility.GrapplingHookItem;
import com.infinium.server.items.custom.tools.voiditems.*;
import com.infinium.server.items.groups.InfiniumFoodComponents;
import com.infinium.server.items.groups.InfiniumItemGroups;
import com.infinium.server.items.materials.InfiniumArmorMaterials;
import com.infinium.server.items.materials.InfiniumToolMaterials;
import com.infinium.server.sounds.InfiniumSounds;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InfiniumItems {
    public static Item VOID_PICKAXE;
    public static Item VOID_SWORD;
    public static Item VOID_AXE;
    public static Item VOID_SHOVEL;
    public static Item VOID_HOE;
    public static Item VOID_BOW;
    public static Item VOID_SHIELD;
    public static Item VOID_HELMET;
    public static Item VOID_CHESTPLATE;
    public static Item VOID_LEGGINGS;
    public static Item VOID_BOOTS;
    public static Item VOID_WINGS;
    public static Item VOID_TOTEM;
    public static Item VOID_EYE;
    public static Item MAGMA_PICKAXE;
    public static Item MAGMA_SWORD;
    public static Item MAGMA_AXE;
    public static Item MAGMA_SHOVEL;
    public static Item MAGMA_HOE;
    public static Item MAGMA_BOW;
    public static Item MAGMA_SHIELD;
    public static Item MAGMA_HELMET;
    public static Item MAGMA_CHESTPLATE;
    public static Item MAGMA_LEGGINGS;
    public static Item MAGMA_BOOTS;
    public static Item MAGMA_WINGS;
    public static Item MAGMA_TOTEM;
    public static Item MAGMA_TRIDENT;
    public static Item IMMUNITY_RUNE;
    public static Item WITHER_RUNE;
    public static Item SPEED_RUNE;
    public static Item RESISTANCE_RUNE;
    public static Item FIRE_RUNE;
    public static Item ENDER_WAND;
    public static Item GRAPPLING_HOOK;
    public static Item SANITY_PILL;
    public static Item  NETHERITE_CARROT;
    public static Item EMPANADA_CARNE;
    public static Item DUET_MUSIC_DISC;
    public static Item FALL_IN_LOVE_MUSIC_DISC;
    public static Item SOUL_SPEED_MERGER;
    public static Item LOOTING_MERGER;
    public static Item KAIROS_CLOCK;
    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), item);
    }

    public static void init() {
        registerVoidArmor();
        registerVoidTools();
        registerMagmaArmor();
        registerMagmaTools();
        registerMusicDiscs();
        registerTotems();
        registerRunes();
        registerMisc();
        registerFood();
    }

    private static void registerVoidArmor(){
        VOID_HELMET = registerItem("void_helmet", new VoidArmorItem(InfiniumArmorMaterials.VOID, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
        VOID_CHESTPLATE = registerItem("void_chestplate", new VoidArmorItem(InfiniumArmorMaterials.VOID, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxDamage(2770).fireproof()));
        VOID_LEGGINGS = registerItem("void_leggings", new VoidArmorItem(InfiniumArmorMaterials.VOID, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxDamage(2500).fireproof()));
        VOID_BOOTS = registerItem("void_boots", new VoidArmorItem(InfiniumArmorMaterials.VOID, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
        VOID_WINGS = registerItem("void_wings", new VoidElytraItem(InfiniumArmorMaterials.VOID_ELYTRA, EquipmentSlot.CHEST,new FabricItemSettings().group(ItemGroup.TRANSPORTATION).rarity(Rarity.RARE).maxDamage(2350).fireproof().equipmentSlot(context -> EquipmentSlot.CHEST)));
    }

    private static void registerVoidTools(){
        VOID_SWORD = registerItem("void_sword", new VoidSwordItem(InfiniumToolMaterials.VOID, 5, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxCount(4570).fireproof()));
        VOID_PICKAXE = registerItem("void_pickaxe", new VoidPickaxeItem(InfiniumToolMaterials.VOID, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_AXE = registerItem("void_axe", new VoidAxeItem(InfiniumToolMaterials.VOID, 7, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_SHOVEL = registerItem("void_shovel", new VoidShovelItem(InfiniumToolMaterials.VOID, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_HOE = registerItem("void_hoe", new VoidHoeItem(InfiniumToolMaterials.VOID, -4, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_BOW = registerItem("void_bow", new VoidBowItem(new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_SHIELD = registerItem("void_shield", new FabricShieldItem(new FabricItemSettings().maxDamage(4570).group(ItemGroup.COMBAT), 10, 16, Items.NETHERITE_INGOT));
    }

    private static void registerMagmaArmor(){
        MAGMA_HELMET = registerItem("magma_helmet", new MagmaArmorItem(InfiniumArmorMaterials.MAGMA, EquipmentSlot.HEAD, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4350).fireproof()));
        MAGMA_CHESTPLATE = registerItem("magma_chestplate", new MagmaArmorItem(InfiniumArmorMaterials.MAGMA, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4770).fireproof()));
        MAGMA_LEGGINGS = registerItem("magma_leggings", new MagmaArmorItem(InfiniumArmorMaterials.MAGMA, EquipmentSlot.LEGS, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4500).fireproof()));
        MAGMA_BOOTS = registerItem("magma_boots", new MagmaArmorItem(InfiniumArmorMaterials.MAGMA, EquipmentSlot.FEET, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4350).fireproof()));
        MAGMA_WINGS = registerItem("magma_wings", new MagmaElytraItem(InfiniumArmorMaterials.MAGMA_ELYTRA, EquipmentSlot.CHEST, new FabricItemSettings().group(ItemGroup.TRANSPORTATION).rarity(Rarity.UNCOMMON).maxDamage(4500).fireproof().equipmentSlot(context -> EquipmentSlot.CHEST)));
    }

    private static void registerMagmaTools(){
        MAGMA_SWORD = registerItem("magma_sword", new MagmaSwordItem(InfiniumToolMaterials.MAGMA, 7, -2.4F, new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_PICKAXE = registerItem("magma_pickaxe", new MagmaPickaxeItem(InfiniumToolMaterials.MAGMA, 1, -2.8F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_AXE = registerItem("magma_axe", new MagmaAxeItem(InfiniumToolMaterials.MAGMA, 9, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_SHOVEL = registerItem("magma_shovel", new MagmaShovelItem(InfiniumToolMaterials.MAGMA, 1.5F, -3.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_HOE = registerItem("magma_hoe", new MagmaHoeItem(InfiniumToolMaterials.MAGMA, -4, 0.0F, new FabricItemSettings().group(ItemGroup.TOOLS).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_BOW = registerItem("magma_bow", new MagmaBowItem(new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof()));
        MAGMA_TRIDENT = registerItem("magma_trident", new MagmaTridentItem(new FabricItemSettings().group(ItemGroup.COMBAT).rarity(Rarity.UNCOMMON).maxDamage(4570).fireproof(), InfiniumEntityType.MAGMA_TRIDENT));
        MAGMA_SHIELD = registerItem("magma_shield", new FabricShieldItem(new FabricItemSettings().maxDamage(4570).group(ItemGroup.COMBAT), 0, 16, Items.NETHERITE_INGOT));
    }

    private static void registerMisc() {
        KAIROS_CLOCK = registerItem("kairos_clock", new Item(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxCount(1)));
        GRAPPLING_HOOK = registerItem("grappling_hook", new GrapplingHookItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(450)));
        ENDER_WAND = registerItem("ender_wand", new EnderWandItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(225)));
        VOID_EYE = registerItem("void_eye", new VoidEyeItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxCount(64)));
        SOUL_SPEED_MERGER = registerItem("soul_speed_merger", new MergerItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxCount(1), Enchantments.SOUL_SPEED, 6));
        LOOTING_MERGER = registerItem("looting_merger", new MergerItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxCount(1), Enchantments.LOOTING, 6));
    }

    private static void registerMusicDiscs(){
        DUET_MUSIC_DISC = registerItem("duet_music_disc", new InfiniumDiscItem(4, InfiniumSounds.DUET, new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1)));
        FALL_IN_LOVE_MUSIC_DISC = registerItem("fall_in_love_music_disc", new InfiniumDiscItem(4, InfiniumSounds.FALL_IN_LOVE, new FabricItemSettings().group(ItemGroup.MISC).rarity(Rarity.EPIC).maxCount(1)));
    }

    private static void registerTotems() {
        VOID_TOTEM = registerItem("void_totem", new InfiniumTotemItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxCount(1).rarity(Rarity.RARE)));
        MAGMA_TOTEM = registerItem("magma_totem", new InfiniumTotemItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxCount(1).rarity(Rarity.UNCOMMON)));
    }

    private static void registerRunes() {
        IMMUNITY_RUNE = registerItem("immunity_rune", new RuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC), InfiniumEffects.IMMUNITY, 20 * 30, 20 * (60 * 6)));
        RESISTANCE_RUNE = registerItem("resistance_rune", new RuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC), StatusEffects.RESISTANCE, 20 * 60, 20 * (60 * 4), 3));
        SPEED_RUNE = registerItem("speed_rune", new RuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC), StatusEffects.SPEED, 20 * (60 * 2), 20 * (60 * 2), 4));
        FIRE_RUNE = registerItem("fire_rune", new RuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC), StatusEffects.FIRE_RESISTANCE, 20 * (60 * 10), 20 * (60 * 5)));
        WITHER_RUNE = registerItem("wither_rune", new WitherRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC)));
    }

    private static void registerFood() {
        SANITY_PILL = registerItem("sanity_pill", new SanityItem(new FabricItemSettings().group(ItemGroup.FOOD).food(InfiniumFoodComponents.SANITY_PILL).rarity(Rarity.EPIC)));
        NETHERITE_CARROT = registerItem("netherite_carrot", new NetheriteCarrotItem(new FabricItemSettings().group(ItemGroup.FOOD).food(InfiniumFoodComponents.NETHERITE_CARROT).rarity(Rarity.UNCOMMON)));
        EMPANADA_CARNE = registerItem("empanada_carne", new NetheriteCarrotItem(new FabricItemSettings().group(ItemGroup.FOOD).food(InfiniumFoodComponents.NETHERITE_CARROT).rarity(Rarity.UNCOMMON)));
    }
}
