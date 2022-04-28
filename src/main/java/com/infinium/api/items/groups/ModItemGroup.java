package com.infinium.api.items.groups;


import com.infinium.Infinium;
import com.infinium.api.items.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup INFINIUM_GROUP = FabricItemGroupBuilder.build(new Identifier(Infinium.MOD_ID, "infinium"), () -> new ItemStack(ModItems.GRAPPLING_HOOK));

}
