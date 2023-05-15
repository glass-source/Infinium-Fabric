package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulcreeper;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.client.renderer.mobs.hostile.InfiniumCreeperEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.EnergySwirlOverlayFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GhoulCreeperEntityRenderer extends MobEntityRenderer<CreeperEntity, InfiniumCreeperEntityModel<CreeperEntity>> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID,"textures/entity/ghoul_creeper/ghoul_creeper.png");
    public GhoulCreeperEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfiniumCreeperEntityModel<>(context.getPart(InfiniumModelLayers.INFINIUM_CREEPER)), 0.5F);
        this.addFeature(new GhoulCreeperChargeFeatureRenderer(this, context.getModelLoader()));

    }
    public Identifier getTexture(CreeperEntity creeperEntity) {
        return TEXTURE;
    }

    @Environment(EnvType.CLIENT)
    public static class GhoulCreeperChargeFeatureRenderer extends EnergySwirlOverlayFeatureRenderer<CreeperEntity, InfiniumCreeperEntityModel<CreeperEntity>> {
        private static final Identifier SKIN = new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_creeper/ghoul_creeper_armor.png");
        private final CreeperEntityModel<CreeperEntity> model;

        public GhoulCreeperChargeFeatureRenderer(FeatureRendererContext<CreeperEntity, InfiniumCreeperEntityModel<CreeperEntity>> context, EntityModelLoader loader) {
            super(context);
            this.model = new CreeperEntityModel<>(loader.getModelPart(EntityModelLayers.CREEPER_ARMOR));
        }

        protected float getEnergySwirlX(float partialAge) {
            return partialAge * 0.01F;
        }

        protected Identifier getEnergySwirlTexture() {
            return SKIN;
        }

        protected EntityModel<CreeperEntity> getEnergySwirlModel() {
            return this.model;
        }
    }

}
