package com.infinium.client.renderer.mobs.hostile.nightmare.nightmareblaze;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.InfiniumModelLayers;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareBlazeEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class NightmareBlazeEntityRenderer extends MobEntityRenderer<NightmareBlazeEntity, NightmareBlazeEntityModel> {
    private static final Identifier TEXTURE = Infinium.id("textures/entity/nightmare_blaze/nightmare_blaze.png");

    public NightmareBlazeEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NightmareBlazeEntityModel(context.getPart(InfiniumModelLayers.NIGHTMARE_BLAZE)), 0.5F);
    }

    protected int getBlockLight(NightmareBlazeEntity blazeEntity, BlockPos blockPos) {
        return 15;
    }

    public Identifier getTexture(NightmareBlazeEntity blazeEntity) {
        return TEXTURE;
    }
}