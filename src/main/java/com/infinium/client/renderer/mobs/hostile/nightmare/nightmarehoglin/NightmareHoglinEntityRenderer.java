package com.infinium.client.renderer.mobs.hostile.nightmare.nightmarehoglin;

import com.infinium.Infinium;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HoglinEntityRenderer;
import net.minecraft.entity.mob.HoglinEntity;
import net.minecraft.util.Identifier;

public class NightmareHoglinEntityRenderer extends HoglinEntityRenderer {
    private static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID,"textures/entity/nightmare_hoglin/nightmare_hoglin.png");

    public NightmareHoglinEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    public Identifier getTexture(HoglinEntity hoglinEntity) {
        return TEXTURE;
    }

    protected boolean isShaking(HoglinEntity hoglinEntity) {
        return super.isShaking(hoglinEntity) || hoglinEntity.canConvert();
    }
}
