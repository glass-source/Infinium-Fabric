package com.infinium.api.utils;

import com.infinium.api.items.global.InfiniumItems;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModelPredicateProvider {

    public static void registerModels() {
        registerBow(InfiniumItems.VOID_BOW);
    }


    private static void registerBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, new Identifier("pull"),
                ((stack, world, entity, seed) -> {
                    if (entity == null) return  0.0f;

                    if (entity.getActiveItem() != stack) return 0.0f;

                    return (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
                }));

        ModelPredicateProviderRegistry.register(bow, new Identifier("pulling"),
        ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f));

    }


}
