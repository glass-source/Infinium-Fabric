package com.infinium.client.renderer;

import com.infinium.client.renderer.item.InfiniumElytraFeatureRenderer;
import com.infinium.client.renderer.item.MagmaTridentItemRenderer;
import com.infinium.client.renderer.item.MagmaTridentRenderer;
import com.infinium.client.renderer.mobs.ghoulmobs.ghoulspider.GhoulSpiderEntityRenderer;
import com.infinium.client.renderer.mobs.ghoulmobs.ghoulzombie.GhoulZombieEntityRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidenderman.VoidEndermanEntityRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidghast.VoidGhastEntityModel;
import com.infinium.client.renderer.mobs.voidmobs.voidghast.VoidGhastEntityRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidspider.VoidSpiderEntityRenderer;
import com.infinium.client.renderer.mobs.voidmobs.voidzombie.VoidZombieEntityRenderer;
import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.items.InfiniumItems;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModelPredicateProvider {

    public void init() {
        initItemModels();
        registerEntityRenderer();
    }
    private void initItemModels() {
        registerBow(InfiniumItems.VOID_BOW);
        registerBow(InfiniumItems.MAGMA_BOW);
        registerModelItems();
    }

    private void registerEntityRenderer() {
        registerVoidMobRenderer();
        registerGhoulMobsRenderer();
        registerMagmaTrident();
        registerEntityModelLayers();
    }

    private void registerMagmaTrident() {
        Identifier tridentId = Registry.ITEM.getId(InfiniumItems.MAGMA_TRIDENT);
        Identifier texture = new Identifier(tridentId.getNamespace(), "textures/entity/" + tridentId.getPath() + ".png");
        EntityModelLayer modelLayer = EntityModelLayers.TRIDENT;
        MagmaTridentItemRenderer tridentItemRenderer = new MagmaTridentItemRenderer(tridentId, texture, modelLayer);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(tridentItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(InfiniumItems.MAGMA_TRIDENT, tridentItemRenderer);
        EntityRendererRegistry.register(InfiniumEntityType.MAGMA_TRIDENT, ctx -> new MagmaTridentRenderer(ctx, texture, modelLayer));
        FabricModelPredicateProviderRegistry.register(InfiniumItems.MAGMA_TRIDENT, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(tridentId + "_in_inventory", "inventory")));
    }

    private void registerVoidMobRenderer(){
        EntityRendererRegistry.register(InfiniumEntityType.VOID_ZOMBIE, VoidZombieEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_GHAST, VoidGhastEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_SPIDER, VoidSpiderEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_ENDERMAN, VoidEndermanEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_ZOMBIE, VoidZombieEntityRenderer::new);
    }

    private void registerGhoulMobsRenderer(){
        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_SPIDER, GhoulSpiderEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_ZOMBIE, GhoulZombieEntityRenderer::new);
    }

    private void registerEntityModelLayers(){
        EntityModelLayerRegistry.registerModelLayer(VoidGhastEntityRenderer.VOID_GHAST, VoidGhastEntityModel::getTexturedModelData);
    }

    private void registerModelItems(){
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, s) -> {
            var model = entityRenderer.getModel();
            if (model instanceof BipedEntityModel<? extends LivingEntity>) {
                registrationHelper.register(new InfiniumElytraFeatureRenderer<>(entityRenderer));
            }
        });
    }

    private void registerBow(Item bow) {
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
