package com.infinium.client.renderer.mobs.hostile.raidmobs.raider;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumPillagerModel;
import com.infinium.server.entities.mobs.hostile.raidmobs.raider.RaiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

public class RaiderEntityRenderer extends IllagerEntityRenderer<RaiderEntity> {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID,"textures/entity/raider/raider.png");

    public RaiderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfiniumPillagerModel<>(context.getPart(InfiniumPillagerModel.PILLAGER_INFINIUM)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }

    public Identifier getTexture(RaiderEntity pillagerEntity) {
        return TEXTURE;
    }
}
