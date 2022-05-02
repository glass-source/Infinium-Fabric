package com.infinium.api.items.global;

import com.infinium.Infinium;
import com.infinium.api.items.custom.armor.VoidArmor;
import com.infinium.api.items.custom.food.SanityItem;
import com.infinium.api.items.custom.misc.EnderWandItem;
import com.infinium.api.items.custom.misc.GrapplingHookItem;
import com.infinium.api.items.custom.misc.MagmaTridentItem;
import com.infinium.api.items.custom.misc.VoidTotemItem;
import com.infinium.api.items.custom.runes.*;
import com.infinium.api.items.custom.tools.VoidPickaxeItem;
import com.infinium.api.items.materials.InfiniumArmorMaterials;
import com.infinium.api.items.materials.InfiniumToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InfiniumItems {

    public static Item VOID_TOTEM;
    public static Item MAGMA_TRIDENT;
    public static Item GRAPPLING_HOOK;
    public static Item VOID_PICKAXE;
    public static Item VOID_HELMET;
    public static Item VOID_CHESTPLATE;
    public static Item VOID_LEGGINGS;
    public static Item VOID_BOOTS;

    public static Item IMMUNITY_RUNE;
    public static Item WITHER_RUNE;
    public static Item SPEED_RUNE;
    public static Item RESISTANCE_RUNE;
    public static Item FIRE_RUNE;

    public static Item ENDER_WAND;

    public static Item SANITY_PILL;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), item);
    }

    public static void init() {
        registerArmor();
        registerTools();
        registerTotems();
        registerRunes();
        registerMisc();
        registerFood();
    }

    private static void registerArmor(){
        VOID_BOOTS = registerItem("void_boots", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.FEET, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
        VOID_LEGGINGS = registerItem("void_leggings", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.LEGS, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2500).fireproof()));
        VOID_CHESTPLATE = registerItem("void_chestplate", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.CHEST, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2770).fireproof()));
        VOID_HELMET = registerItem("void_helmet", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.HEAD, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
    }

    private static void registerTools(){
        VOID_PICKAXE = registerItem("void_pickaxe", new VoidPickaxeItem(InfiniumToolMaterials.VOID, 0, 0, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
    }

    private static void registerMisc() {
        GRAPPLING_HOOK = registerItem("grappling_hook", new GrapplingHookItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(450)));
        MAGMA_TRIDENT = registerItem("magma_trident", new MagmaTridentItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.UNCOMMON).maxDamage(3450).fireproof()));
        ENDER_WAND = registerItem("ender_wand", new EnderWandItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(25)));
    }

    private static void registerTotems() {
        VOID_TOTEM = registerItem("void_totem", new VoidTotemItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxCount(1).rarity(Rarity.RARE)));
    }

    private static void registerRunes() {
        IMMUNITY_RUNE = registerItem("immunity_rune", new ImmunityRuneItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(8).rarity(Rarity.EPIC)));
        WITHER_RUNE = registerItem("wither_rune", new WitherRuneItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(8).rarity(Rarity.EPIC)));
        SPEED_RUNE = registerItem("speed_rune", new SpeedRuneItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(8).rarity(Rarity.EPIC)));
        RESISTANCE_RUNE = registerItem("resistance_rune", new ResistanceRuneItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(8).rarity(Rarity.EPIC)));
        FIRE_RUNE = registerItem("fire_rune", new FireRuneItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(8).rarity(Rarity.EPIC)));
    }

    private static void registerFood() {
        SANITY_PILL = registerItem("sanity_pill", new SanityItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).food(InfiniumFoodComponents.SANITY_PILL).rarity(Rarity.EPIC)));
    }

}
