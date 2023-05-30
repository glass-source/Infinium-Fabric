package com.infinium.client.renderer.mobs.hostile.raidmobs.raider;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.client.renderer.mobs.hostile.raidmobs.InfiniumPillagerModel;
import com.infinium.server.entities.mobs.hostile.raidmobs.RaiderEntity;
import com.infinium.server.world.dimensions.InfiniumDimensions;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

public class RaiderEntityRenderer extends IllagerEntityRenderer<RaiderEntity> {

    private static final Identifier TEXTURE_NORMAL = new Identifier(Infinium.MOD_ID,"textures/entity/raider/raider.png");
    private static final Identifier TEXTURE_VOID = new Identifier(Infinium.MOD_ID,"textures/entity/raider/void_raider.png");


    public RaiderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new InfiniumPillagerModel<>(context.getPart(InfiniumModelLayers.INFINIUM_PILLAGER)), 0.5F);
        this.addFeature(new HeldItemFeatureRenderer<>(this));
    }
    public Identifier getTexture(RaiderEntity pillagerEntity) {
        return pillagerEntity.getWorld().getRegistryKey().equals(InfiniumDimensions.THE_VOID) ? TEXTURE_VOID : TEXTURE_NORMAL;
    }
}
