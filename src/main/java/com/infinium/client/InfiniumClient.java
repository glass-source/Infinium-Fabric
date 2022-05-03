package com.infinium.client;


import com.infinium.api.items.custom.misc.MagmaTridentItem;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.utils.ModelPredicateProvider;
import com.infinium.client.render.entity.InfiniumTridentEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.fabric.FabricClientAudiences;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@Environment(EnvType.CLIENT)
public class InfiniumClient implements ClientModInitializer {

    private static final MagmaTridentItem ITEM = (MagmaTridentItem) InfiniumItems.MAGMA_TRIDENT;
    private final Audience client = FabricClientAudiences.of().audience();


    @Override
    public void onInitializeClient() {
        ModelPredicateProvider.registerModels();

    }

    private static void registerTrident() {
        Identifier tridentId = Registry.ITEM.getId(InfiniumItems.MAGMA_TRIDENT);
        Identifier texture = new Identifier(tridentId.getNamespace(), "textures/entity/magma_trident.png");
        EntityModelLayer modelLayer = EntityModelLayers.TRIDENT;
        InfiniumTridentItemRenderer tridentItemRenderer = new InfiniumTridentItemRenderer(tridentId, texture, modelLayer);
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(tridentItemRenderer);
        BuiltinItemRendererRegistry.INSTANCE.register(InfiniumItems.MAGMA_TRIDENT, tridentItemRenderer);
        EntityRendererRegistry.INSTANCE.register(ITEM.getEntityType(), ctx -> new InfiniumTridentEntityRenderer(ctx, texture, modelLayer));
        FabricModelPredicateProviderRegistry.register(ITEM, new Identifier("throwing"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> out.accept(new ModelIdentifier(tridentId + "_in_inventory", "inventory")));
    }
}
