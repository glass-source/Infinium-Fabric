package com.infinium.api.utils;

import com.infinium.api.entities.InfiniumEntityType;
import com.infinium.api.entities.render.voidghast.VoidGhastEntityRenderer;
import com.infinium.api.items.global.InfiniumItems;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.GhastEntityRenderer;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModelPredicateProvider {

    public static void registerBowModels() {
        registerBow(InfiniumItems.VOID_BOW);
        registerBow(InfiniumItems.MAGMA_BOW);
    }

    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.VOID_GHAST, VoidGhastEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.MAGMA_TRIDENT, TridentEntityRenderer::new);
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
