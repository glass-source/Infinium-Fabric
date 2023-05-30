package com.infinium.client.renderer.mobs.hostile.nightmare.nightmarebrute;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.nightmare.NightmareBruteEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class NightmareBruteEntityRenderer extends BipedEntityRenderer<NightmareBruteEntity, PiglinEntityModel<NightmareBruteEntity>> {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/nightmare_brute/nightmare_brute.png");
    public NightmareBruteEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PiglinEntityModel<>(ctx.getPart(EntityModelLayers.PIGLIN_BRUTE)), 1);
    }
    public Identifier getTexture(NightmareBruteEntity mobEntity) {
        return TEXTURE;
    }

    protected boolean isShaking(NightmareBruteEntity mobEntity) {
        return super.isShaking(mobEntity) || mobEntity != null && mobEntity.shouldZombify();
    }


}
