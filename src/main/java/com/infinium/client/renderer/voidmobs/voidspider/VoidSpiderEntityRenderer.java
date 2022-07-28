package com.infinium.client.renderer.voidmobs.voidspider;

import com.infinium.Infinium;
import com.infinium.global.entities.mobs.voidmobs.VoidSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SpiderEyesFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class VoidSpiderEntityRenderer<T extends VoidSpiderEntity> extends MobEntityRenderer<T, VoidSpiderEntityModel<T>>  {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/void_spider/void_spider.png");
    public static final EntityModelLayer VOID_SPIDER = new EntityModelLayer(new Identifier(Infinium.MOD_ID, "void_spider"), "void_spider");

    public VoidSpiderEntityRenderer(EntityRendererFactory.Context context) {
        this(context, VOID_SPIDER);
    }

    public VoidSpiderEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, new VoidSpiderEntityModel<>(ctx.getPart(layer)), 0.8F);
    }

    @Override
    public Identifier getTexture(VoidSpiderEntity entity) {
        return TEXTURE;
    }


}
