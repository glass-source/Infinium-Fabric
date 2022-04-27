package com.infinium.items;

import com.infinium.Infinium;
import com.infinium.items.custom.armor.VoidArmor;
import com.infinium.items.custom.misc.MagmaTridentItem;
import com.infinium.items.custom.tools.VoidPickaxeItem;
import com.infinium.items.custom.misc.GrapplingHookItem;
import com.infinium.items.groups.ModItemGroup;
import com.infinium.items.materials.ModArmorMaterials;
import com.infinium.items.materials.ModToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item MAGMA_TRIDENT = registerItem("magma_trident",
            new MagmaTridentItem(new FabricItemSettings()
                    .group(ModItemGroup.TEST)
                    .rarity(Rarity.UNCOMMON)
                    .maxDamage(3450)
                    .fireproof()));

    public static final Item VOID_PICKAXE = registerItem("void_pickaxe",
            new VoidPickaxeItem(ModToolMaterials.VOID, 20, 20,
                    new FabricItemSettings()
                            .group(ModItemGroup.TEST)
                            .rarity(Rarity.EPIC)
                            .maxDamage(2000)
                            .fireproof()));

    public static final Item GRAPPLING_HOOK = registerItem("grappling_hook",
            new GrapplingHookItem(new FabricItemSettings()
                    .group(ModItemGroup.TEST)
                    .maxDamage(20)));

    public static final Item VOID_HELMET = registerItem("void_helmet",
            new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.HEAD,
                    new FabricItemSettings()
                            .group(ModItemGroup.TEST)
                            .rarity(Rarity.EPIC)
                            .maxDamage(2500)
                            .fireproof()));

    public static final Item VOID_CHESTPLATE = registerItem("void_chestplate",
            new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.CHEST,
                    new FabricItemSettings()
                            .group(ModItemGroup.TEST)
                            .rarity(Rarity.EPIC)
                            .maxDamage(2770)
                            .fireproof()));

    public static final Item VOID_LEGGINGS = registerItem("void_leggings",
            new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.LEGS,
                    new FabricItemSettings()
                            .group(ModItemGroup.TEST)
                            .rarity(Rarity.EPIC)
                            .maxDamage(2500)
                            .fireproof()));

    public static final Item VOID_BOOTS = registerItem("void_boots",
            new VoidArmor(ModArmorMaterials.VOID, EquipmentSlot.FEET,
                    new FabricItemSettings()
                            .group(ModItemGroup.TEST)
                            .rarity(Rarity.EPIC)
                            .maxDamage(2500)
                            .fireproof()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Infinium.LOGGER.info("Registrando items para: " + Infinium.MOD_ID);
    }

}
