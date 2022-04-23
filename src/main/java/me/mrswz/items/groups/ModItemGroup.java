package me.mrswz.items.groups;


import me.mrswz.Test;
import me.mrswz.items.ModItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup TEST = FabricItemGroupBuilder.build(new Identifier(Test.MOD_ID, "test"), () -> new ItemStack(ModItems.GRAPPLING_HOOK));

}
