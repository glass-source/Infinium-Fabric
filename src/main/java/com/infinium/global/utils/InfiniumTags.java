package com.infinium.global.utils;

import com.infinium.Infinium;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumTags {

    public static void initTags(){
        Blocks.init();
        Items.init();
    }

    //Example of a Block TagKey
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

    //Example of an Item TagKey
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
