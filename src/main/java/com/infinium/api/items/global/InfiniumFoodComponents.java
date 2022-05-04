package com.infinium.api.items.global;

import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;

import java.util.Random;

public class InfiniumFoodComponents {

    public static FoodComponent SANITY_PILL = (new FoodComponent.Builder()).hunger(1).saturationModifier(0.2F).alwaysEdible().build();
    public static FoodComponent NETHERITE_CARROT = (new FoodComponent.Builder()).hunger(12).saturationModifier(2.4F).alwaysEdible().build();

}
