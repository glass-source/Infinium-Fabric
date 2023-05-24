package com.infinium.server.items.groups;

import net.minecraft.item.FoodComponent;

public class InfiniumFoodComponents {

    public static FoodComponent SANITY_PILL = (new FoodComponent.Builder()).hunger(1).saturationModifier(0).alwaysEdible().build();
    public static FoodComponent NETHERITE_CARROT = (new FoodComponent.Builder()).hunger(12).saturationModifier(2.5F).alwaysEdible().build();
}
