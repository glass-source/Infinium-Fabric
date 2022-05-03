package com.infinium.api.utils;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.impl.datagen.FabricTagBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumTags {

    public static class Blocks{

        public static TagKey<Block> ENDER_WAND_TRAVELLED_BLOCKS;

        public static void init() {
            ENDER_WAND_TRAVELLED_BLOCKS = createTag("ender_wand_travelled_blocks");
        }

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier(Infinium.MOD_ID, name));
        }

        private static TagKey<Block> createCommonTag(String name) {
            return TagKey.of(Registry.BLOCK_KEY, new Identifier("c", name));
        }

    }

    public static class Items{
        public static TagKey<Item> ENDER_WAND_USES;

        public static void init() {
            ENDER_WAND_USES = createTag("ender_wand_uses");
        }

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier(Infinium.MOD_ID, name));
        }

        private static TagKey<Item> createCommonTag(String name) {
            return TagKey.of(Registry.ITEM_KEY, new Identifier("c", name));
        }
    }
}
