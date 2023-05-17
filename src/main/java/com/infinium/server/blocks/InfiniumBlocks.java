package com.infinium.server.blocks;

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

    private static Block registerBlock(String name, Block block, ItemGroup group) {
        registerBlockItem(name, block, group);
        return Registry.register(Registry.BLOCK, new Identifier(Infinium.MOD_ID, name), block);
    }
    private static void registerBlockItem(String name, Block block, ItemGroup group) {
        Registry.register(Registry.ITEM, new Identifier(Infinium.MOD_ID, name), new BlockItem(block, new FabricItemSettings().group(group)));
    }

    public static void init() {
        registerBlocks();
    }

    private static void registerBlocks() {
        VOID_STONE = registerBlock("void_stone", new Block(FabricBlockSettings.of(Material.METAL).strength(2.25F).requiresTool()), ItemGroup.BUILDING_BLOCKS);
        VOID_STONE_ORE = registerBlock("void_stone_ore", new Block(FabricBlockSettings.of(Material.METAL).strength(1.5F).requiresTool()), ItemGroup.BUILDING_BLOCKS);
    }

}
