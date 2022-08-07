package com.infinium.client.renderer;

import com.infinium.client.renderer.item.InfiniumElytraFeatureRenderer;
import com.infinium.client.renderer.mobs.models.InfiniumSpiderEntityModel;
import com.infinium.client.renderer.projectiles.magmatrident.MagmaTridentEntityRenderer;
import com.infinium.client.renderer.item.MagmaTridentItemRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidghast.VoidGhastEntityModel;
import com.infinium.client.renderer.mobs.voidmobs.voidghast.VoidGhastEntityRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidspider.VoidSpiderEntityRenderer;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.groups.InfiniumItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ModelPredicateProvider {

    public static void initItemModels() {
        registerBow(InfiniumItems.VOID_BOW);
        registerBow(InfiniumItems.MAGMA_BOW);
        registerModelItems();
    }

    public static void registerEntityRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.VOID_GHAST, VoidGhastEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.MAGMA_TRIDENT, MagmaTridentEntityRenderer::new);
    }

    public static void registerEntityModelLayers(){
        EntityModelLayerRegistry.registerModelLayer(VoidGhastEntityRenderer.VOID_GHAST, VoidGhastEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(VoidSpiderEntityRenderer.VOID_SPIDER, InfiniumSpiderEntityModel::getTexturedModelData);
    }

    private static void registerModelItems(){
        BuiltinItemRendererRegistry.INSTANCE.register(InfiniumItems.MAGMA_TRIDENT, MagmaTridentItemRenderer::render);

        ModelPredicateProviderRegistry.register(InfiniumItems.MAGMA_TRIDENT, new Identifier("throwing"), ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F));
        ModelPredicateProviderRegistry.register(InfiniumItems.MAGMA_SHIELD, new Identifier("blocking"), ((stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F));

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, s) -> {
            var model = entityRenderer.getModel();
            if (model instanceof BipedEntityModel<? extends LivingEntity>) {
                registrationHelper.register(new InfiniumElytraFeatureRenderer<>(entityRenderer));
            }
        });

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
