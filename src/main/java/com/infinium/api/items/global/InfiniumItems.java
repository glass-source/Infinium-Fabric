package com.infinium.api.items.global;

import com.infinium.Infinium;
import com.infinium.api.entity.types.InfiniumEntityTypes;
import com.infinium.api.items.custom.armor.VoidArmor;
import com.infinium.api.items.custom.food.SanityItem;
import com.infinium.api.items.custom.misc.EnderWandItem;
import com.infinium.api.items.custom.misc.GrapplingHookItem;
import com.infinium.api.items.custom.misc.MagmaTridentItem;
import com.infinium.api.items.custom.misc.VoidTotemItem;
import com.infinium.api.items.custom.runes.*;
import com.infinium.api.items.custom.tools.voiditems.*;
import com.infinium.api.items.materials.InfiniumArmorMaterials;
import com.infinium.api.items.materials.InfiniumToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class InfiniumItems {

    public static Item MAGMA_TRIDENT;

    public static Item VOID_PICKAXE;
    public static Item VOID_SWORD;
    public static Item VOID_AXE;
    public static Item VOID_SHOVEL;
    public static Item VOID_HOE;
    public static Item VOID_BOW;


    public static Item VOID_HELMET;
    public static Item VOID_CHESTPLATE;
    public static Item VOID_LEGGINGS;
    public static Item VOID_BOOTS;

    public static Item VOID_TOTEM;

    public static Item IMMUNITY_RUNE;
    public static Item WITHER_RUNE;
    public static Item SPEED_RUNE;
    public static Item RESISTANCE_RUNE;
    public static Item FIRE_RUNE;

    public static Item ENDER_WAND;
    public static Item GRAPPLING_HOOK;

    public static Item SANITY_PILL;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), item);
    }

    public static void init() {
        registerArmor();
        registerVoidTools();
        registerTotems();
        registerRunes();
        registerMisc();
        registerFood();
    }

    private static void registerArmor(){
        VOID_HELMET = registerItem("void_helmet", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.HEAD, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
        VOID_CHESTPLATE = registerItem("void_chestplate", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.CHEST, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2770).fireproof()));
        VOID_LEGGINGS = registerItem("void_leggings", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.LEGS, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2500).fireproof()));
        VOID_BOOTS = registerItem("void_boots", new VoidArmor(InfiniumArmorMaterials.VOID, EquipmentSlot.FEET, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(2350).fireproof()));
    }

    private static void registerVoidTools(){
        VOID_SWORD = registerItem("void_sword", new VoidSwordItem(InfiniumToolMaterials.VOID, 6, -2.4F, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_PICKAXE = registerItem("void_pickaxe", new VoidPickaxeItem(InfiniumToolMaterials.VOID, 1, -2.8F, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_AXE = registerItem("void_axe", new VoidAxeItem(InfiniumToolMaterials.VOID, 5, -3.0F, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_SHOVEL = registerItem("void_shovel", new VoidShovelItem(InfiniumToolMaterials.VOID, 0, 0, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_HOE = registerItem("void_hoe", new VoidHoeItem(InfiniumToolMaterials.VOID, -4, 0.0F, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
        VOID_BOW = registerItem("void_bow", new VoidBowItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.RARE).maxDamage(4570).fireproof()));
    }

    private static void registerMisc() {
        GRAPPLING_HOOK = registerItem("grappling_hook", new GrapplingHookItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(450)));
        MAGMA_TRIDENT = registerItem("magma_trident", new MagmaTridentItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.UNCOMMON).maxDamage(3450).fireproof(), InfiniumEntityTypes.MAGMA_TRIDENT));
        ENDER_WAND = registerItem("ender_wand", new EnderWandItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).rarity(Rarity.EPIC).maxDamage(25)));
    }

    private static void registerTotems() {
        VOID_TOTEM = registerItem("void_totem", new VoidTotemItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxCount(1).rarity(Rarity.RARE)));
    }

    private static void registerRunes() {
        IMMUNITY_RUNE = registerItem("immunity_rune", new ImmunityRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(16).rarity(Rarity.EPIC)));
        WITHER_RUNE = registerItem("wither_rune", new WitherRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(350).rarity(Rarity.EPIC)));
        SPEED_RUNE = registerItem("speed_rune", new SpeedRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(100).rarity(Rarity.EPIC)));
        RESISTANCE_RUNE = registerItem("resistance_rune", new ResistanceRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(32).rarity(Rarity.EPIC)));
        FIRE_RUNE = registerItem("fire_rune", new FireRuneItem(InfiniumToolMaterials.VOID, new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).maxDamage(120).rarity(Rarity.EPIC)));
    }

    private static void registerFood() {
        SANITY_PILL = registerItem("sanity_pill", new SanityItem(new FabricItemSettings().group(InfiniumItemGroups.INFINIUM).food(InfiniumFoodComponents.SANITY_PILL).rarity(Rarity.EPIC)));
    }

}
