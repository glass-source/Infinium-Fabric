package com.infinium.client.renderer.mobs.hostile.nightmare.nightmareghast;

import com.infinium.Infinium;
import com.infinium.server.entities.mobs.hostile.nightmare.nightmareghast.NightmareGhastEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

import static com.infinium.client.renderer.mobs.hostile.voidmobs.voidghast.VoidGhastEntityRenderer.VOID_GHAST;

@Environment(EnvType.CLIENT)
public class NightmareGhastEntityRenderer extends MobEntityRenderer<NightmareGhastEntity, NightmareGhastEntityModel> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/nightmare_ghast/nightmare_ghast.png");
    private static final Identifier ANGRY_TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/nightmare_ghast/nightmare_ghast_shooting.png");

    public NightmareGhastEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new NightmareGhastEntityModel(context.getPart(VOID_GHAST)), 1.5F);
    }

    @Override
    public Identifier getTexture(NightmareGhastEntity ghastEntity) {
        return ghastEntity.isShooting() ? ANGRY_TEXTURE : TEXTURE;
    }

    @Override
    protected void scale(NightmareGhastEntity ghastEntity, MatrixStack matrixStack, float f) {
        matrixStack.scale(4.5F, 4.5F, 4.5F);
    }
}
