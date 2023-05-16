package com.infinium.client.renderer.mobs.hostile.nightmare.nightmareskeleton;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.hostile.InfiniumSkeletonEntityModel;
import com.infinium.server.entities.mobs.hostile.nightmare.nightmareskeleton.NightmareSkeletonEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class NightmareSkeletonEntityRenderer extends BipedEntityRenderer<NightmareSkeletonEntity, InfiniumSkeletonEntityModel<NightmareSkeletonEntity>> {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/nightmare_skeleton/nightmare_skeleton.png");

    public NightmareSkeletonEntityRenderer(EntityRendererFactory.Context context) {
        this(context, InfiniumSkeletonEntityModel.INFINIUM_SKELETON);
    }

    public NightmareSkeletonEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, new InfiniumSkeletonEntityModel<>(ctx.getPart(layer)), 0.5F);
    }

    public Identifier getTexture(NightmareSkeletonEntity nightmareSkeletonEntity) {
        return TEXTURE;
    }

    protected boolean isShaking(AbstractSkeletonEntity abstractSkeletonEntity) {
        return abstractSkeletonEntity.isShaking();
    }
}
