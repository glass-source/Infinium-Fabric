package me.mrswz.items;

import me.mrswz.Test;
import me.mrswz.items.custom.tools.FusionPickaxe;
import me.mrswz.items.custom.misc.GrapplingHook;
import me.mrswz.items.groups.ModItemGroup;
import me.mrswz.items.materials.ModToolMaterials;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {

    public static final Item FUSION_PICKAXE = registerItem("fusion_pickaxe",
            new FusionPickaxe(ModToolMaterials.FUSION, 20, 20,
            new FabricItemSettings()
            .group(ModItemGroup.TEST)
            .rarity(Rarity.EPIC)
            .maxDamage(2000)
            .fireproof()));


    public static final Item GRAPPLING_HOOK = registerItem("grappling_hook",
            new GrapplingHook(new FabricItemSettings()
            .group(ModItemGroup.TEST)
            .maxDamage(20)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(Test.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Test.LOGGER.info("Registrando items para: " + Test.MOD_ID);
    }

}
