package com.infinium.client.renderer;

import com.infinium.client.renderer.item.InfiniumElytraFeatureRenderer;
import com.infinium.client.renderer.item.MagmaTridentItemRenderer;
import com.infinium.client.renderer.item.MagmaTridentRenderer;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.client.renderer.mobs.hostile.InfiniumCreeperEntityModel;
import com.infinium.client.renderer.mobs.hostile.InfiniumSkeletonEntityModel;
import com.infinium.client.renderer.mobs.hostile.bosses.supernova.SuperNovaEntityModel;
import com.infinium.client.renderer.mobs.hostile.bosses.supernova.SuperNovaEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.dungeon.pirate.BlackBeardEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.dungeon.pirate.PirateSkeletonEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulcreeper.GhoulCreeperEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulspider.GhoulSpiderEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulwitch.GhoulWitchEntityModel;
import com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulwitch.GhoulWitchEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulzombie.GhoulZombieEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmareblaze.NightmareBlazeEntityModel;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmareblaze.NightmareBlazeEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmarebrute.NightmareBruteEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmareghast.NightmareGhastEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmarehoglin.NightmareHoglinEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.nightmare.nightmareskeleton.NightmareSkeletonEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumEvokerModel;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumPillagerModel;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumVindicatorModel;
import com.infinium.client.renderer.mobs.hostile.raidmobs.berserker.BerserkerEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.raidmobs.raider.RaiderEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.raidmobs.sorcerer.SorcererEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidcreeper.VoidCreeperEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidenderman.VoidEndermanEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidghast.VoidGhastEntityModel;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidghast.VoidGhastEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidskeleton.VoidSkeletonEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidspider.VoidSpiderEntityRenderer;
import com.infinium.client.renderer.mobs.hostile.voidmobs.voidzombie.VoidZombieEntityRenderer;
import com.infinium.client.renderer.mobs.neutral.duck.DuckEntityRenderer;
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
        registerNeutralMobsRenderer();
        registerMagmaTrident();
        registerEntityModelLayers();
        registerRaidMobsRenderer();
        registerDungeonMobsRenderer();
        registerNightmareMobsRenderer();
        registerBossRenderer();
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
        EntityRendererRegistry.register(InfiniumEntityType.VOID_CREEPER, VoidCreeperEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.VOID_SKELETON, VoidSkeletonEntityRenderer::new);
    }

    private void registerGhoulMobsRenderer(){
        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_SPIDER, GhoulSpiderEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_ZOMBIE, GhoulZombieEntityRenderer::new);

        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_CREEPER, GhoulCreeperEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.GHOUL_WITCH, GhoulWitchEntityRenderer::new);
    }

    private void registerNightmareMobsRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.NIGHTMARE_SKELETON, NightmareSkeletonEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.NIGHTMARE_GHAST, NightmareGhastEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.NIGHTMARE_BRUTE, NightmareBruteEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.NIGHTMARE_HOGLIN, NightmareHoglinEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.NIGHTMARE_BLAZE, NightmareBlazeEntityRenderer::new);
    }

    private void registerBossRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.SUPER_NOVA, SuperNovaEntityRenderer::new);
    }
    private void registerRaidMobsRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.EXPLOSIVE_SORCERER, SorcererEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.BERSERKER, BerserkerEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.RAIDER, RaiderEntityRenderer::new);
    }

    private void registerDungeonMobsRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.PIRATE_SKELETON, PirateSkeletonEntityRenderer::new);
        EntityRendererRegistry.register(InfiniumEntityType.BLACK_BEARD, BlackBeardEntityRenderer::new);
    }

    private void registerNeutralMobsRenderer() {
        EntityRendererRegistry.register(InfiniumEntityType.DUCK, DuckEntityRenderer::new);
    }

    private void registerEntityModelLayers(){
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.INFINIUM_EVOKER, InfiniumEvokerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.VINDICATOR_INFINIUM, InfiniumVindicatorModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.INFINIUM_PILLAGER, InfiniumPillagerModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.VOID_GHAST, VoidGhastEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.INFINIUM_CREEPER, InfiniumCreeperEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.INFINIUM_SKELETON, InfiniumSkeletonEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.GHOUL_WITCH, GhoulWitchEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.NIGHTMARE_BLAZE, NightmareBlazeEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(InfiniumModelLayers.SUPER_NOVA, SuperNovaEntityModel::getTexturedModelData);
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
