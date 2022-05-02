package com.infinium.api.items.global;


import com.infinium.Infinium;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class InfiniumItemGroups {

    public static final ItemGroup INFINIUM = FabricItemGroupBuilder.build(new Identifier(Infinium.MOD_ID, "infinium"), () -> new ItemStack(InfiniumItems.GRAPPLING_HOOK));

}
