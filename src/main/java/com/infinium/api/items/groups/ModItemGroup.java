package com.infinium.api.items.groups;


import com.infinium.Infinium;
import com.infinium.api.items.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup TEST = FabricItemGroupBuilder.build(new Identifier(Infinium.MOD_ID, "test"), () -> new ItemStack(ModItems.GRAPPLING_HOOK));

}
