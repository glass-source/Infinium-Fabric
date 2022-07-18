package com.infinium.global.blocks;

import com.infinium.Infinium;
import com.infinium.global.items.groups.InfiniumItemGroups;
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
    public static Block VOID_STONE_BUTTON;
    public static Block VOID_STONE_PRESSURE_PLATE;
    public static Block VOID_STONE_WALL;

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
        VOID_STONE = registerBlock("void_stone", new Block(FabricBlockSettings.of(Material.METAL).strength(2.25F).requiresTool()), InfiniumItemGroups.INFINIUM);
        VOID_STONE_ORE = registerBlock("void_stone_ore", new Block(FabricBlockSettings.of(Material.METAL).strength(1.5F).requiresTool()), InfiniumItemGroups.INFINIUM);
        VOID_STONE_BUTTON = registerBlock("void_stone_button", new StoneButtonBlock(FabricBlockSettings.of(Material.METAL).strength(0.1F).noCollision()), InfiniumItemGroups.INFINIUM);
        VOID_STONE_PRESSURE_PLATE = registerBlock("void_stone_pressure_plate", new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING, FabricBlockSettings.of(Material.METAL).strength(0.1F).noCollision()), InfiniumItemGroups.INFINIUM);
        VOID_STONE_WALL = registerBlock("void_stone_wall", new WallBlock(FabricBlockSettings.of(Material.METAL).strength(0.4F)), InfiniumItemGroups.INFINIUM);
    }

}
