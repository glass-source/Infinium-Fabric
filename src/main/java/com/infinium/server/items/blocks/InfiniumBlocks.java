package com.infinium.server.items.blocks;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumBlocks {
    public static Block VOID_STONE;
    public static Block VOID_STONE_ORE;
    public static Block NIGHTMARE_OBSIDIAN;
    public static Block NIGHTMARE_DOOR;
    public static void init() {
        registerBlocks();
    }
    private static void registerBlocks() {
        NIGHTMARE_OBSIDIAN = registerBlock("nightmare_obsidian", new Block(FabricBlockSettings.of(Material.STONE).strength(-1, 3600000.0F).luminance(14)));
        VOID_STONE_ORE = registerBlock("void_stone_ore", new Block(FabricBlockSettings.of(Material.STONE).strength(-1, 3600000.0F).luminance(14)));
        VOID_STONE = registerBlock("void_stone", new Block(FabricBlockSettings.of(Material.STONE).strength(3, 3).requiresTool()));
        NIGHTMARE_DOOR = registerBlock("nightmare_door", new NightmareDoorBlock(FabricBlockSettings.of(Material.METAL).strength(-1, 3600000.0F).luminance(14).nonOpaque()));
    }
    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registry.BLOCK, new Identifier(Infinium.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    }

}
