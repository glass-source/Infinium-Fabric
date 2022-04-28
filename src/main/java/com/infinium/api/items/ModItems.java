package com.infinium.api.items;

import com.infinium.Infinium;
import com.infinium.api.items.custom.armor.VoidArmor;
import com.infinium.api.items.custom.misc.MagmaTridentItem;
import com.infinium.api.items.custom.misc.VoidTotemItem;
import com.infinium.api.items.custom.tools.VoidPickaxeItem;
import com.infinium.api.items.custom.misc.GrapplingHookItem;
import com.infinium.api.items.groups.ModItemGroup;
import com.infinium.api.items.materials.ModArmorMaterials;
import com.infinium.api.items.materials.ModToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static Item VOID_TOTEM;
    public static Item MAGMA_TRIDENT;
    public static Item GRAPPLING_HOOK;
    public static Item VOID_PICKAXE;
    public static Item VOID_HELMET;
    public static Item VOID_CHESTPLATE;
    public static Item VOID_LEGGINGS;
    public static Item VOID_BOOTS;

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), item);
    }

    public static void init() {
        registerArmor();
        registerTools();
        registerTotems();
    }

    private static void registerArmor(){
        VOID_BOOTS = registerItem("void_boots",
                new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.FEET, 
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.EPIC)
                .maxDamage(2500)
                .fireproof()));
        
        VOID_LEGGINGS = registerItem("void_leggings",
                new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.LEGS, 
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.EPIC)
                .maxDamage(2500)
                .fireproof()));

        VOID_CHESTPLATE = registerItem("void_chestplate",
                new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.CHEST,
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.EPIC)
                .maxDamage(2770)
                .fireproof()));
        
        VOID_HELMET = registerItem("void_helmet",
                new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.HEAD,
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
               .rarity(Rarity.EPIC)
                .maxDamage(2500)
                .fireproof()));
    }
    
    private static void registerTools(){
        VOID_PICKAXE = registerItem("void_pickaxe",
                new VoidPickaxeItem(ModToolMaterials.VOID, 20, 20,
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.UNCOMMON)
                .maxDamage(2000)
                .fireproof()));

        GRAPPLING_HOOK = registerItem("grappling_hook",
                new GrapplingHookItem(new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.UNCOMMON)
                .maxDamage(20)));

        MAGMA_TRIDENT = registerItem("magma_trident",
                new MagmaTridentItem(new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.UNCOMMON)
                .maxDamage(3450)
                .fireproof()));
    }

    private static void registerTotems(){
        VOID_TOTEM = registerItem("void_totem",
                new VoidTotemItem(
                new FabricItemSettings()
                .group(ModItemGroup.INFINIUM)
                .rarity(Rarity.RARE)));
    }

}
