package com.infinium.client.renderer.mobs.ghoulmobs.ghoulspider;

import com.infinium.Infinium;
import com.infinium.client.renderer.mobs.models.InfiniumSpiderEntityModel;
import com.infinium.server.entities.mobs.voidmobs.VoidSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class GhoulSpiderEntityRenderer<T extends VoidSpiderEntity> extends MobEntityRenderer<T, InfiniumSpiderEntityModel<T>>  {

    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/ghoul_spider/ghoul_spider.png");
    public static final EntityModelLayer GHOUL_SPIDER = new EntityModelLayer(new Identifier(Infinium.MOD_ID, "ghoul_spider"), "ghoul_spider");

    public GhoulSpiderEntityRenderer(EntityRendererFactory.Context context) {
        this(context, GHOUL_SPIDER);
    }

    public GhoulSpiderEntityRenderer(EntityRendererFactory.Context ctx, EntityModelLayer layer) {
        super(ctx, new InfiniumSpiderEntityModel<>(ctx.getPart(layer)), 0.8F);
    }

    @Override
    public Identifier getTexture(VoidSpiderEntity entity) {
        return TEXTURE;
    }


}
