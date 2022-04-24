package me.mrswz.items;

import me.mrswz.Test;
import me.mrswz.items.custom.armor.VoidArmor;
import me.mrswz.items.custom.tools.VoidPickaxe;
import me.mrswz.items.custom.misc.GrapplingHook;
import me.mrswz.items.groups.ModItemGroup;
import me.mrswz.items.materials.ModArmorMaterials;
import me.mrswz.items.materials.ModToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item VOID_PICKAXE = registerItem("void_pickaxe",
            new VoidPickaxe(ModToolMaterials.FUSION, 20, 20,
            new FabricItemSettings()
            .group(ModItemGroup.TEST)
            .rarity(Rarity.EPIC)
            .maxDamage(2000)
            .fireproof()));


    public static final Item GRAPPLING_HOOK = registerItem("grappling_hook",
            new GrapplingHook(new FabricItemSettings()
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
        return Registry.register(Registry.ITEM, new Identifier(Test.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Test.LOGGER.info("Registrando items para: " + Test.MOD_ID);
    }

}
