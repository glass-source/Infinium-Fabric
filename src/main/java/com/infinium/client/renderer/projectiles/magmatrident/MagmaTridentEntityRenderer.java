package com.infinium.client.renderer.projectiles.magmatrident;

import com.infinium.Infinium;
import com.infinium.server.entities.projectiles.MagmaTridentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class MagmaTridentEntityRenderer extends EntityRenderer<MagmaTridentEntity> {

    public static final Identifier TEXTURE = new Identifier(Infinium.MOD_ID, "textures/entity/magma_trident.png");
    private static MagmaTridentEntityModel model = null;
    private static MagmaTridentEntity tridentEntity;
    private static float n1;

    public MagmaTridentEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        model = new MagmaTridentEntityModel(context.getPart(EntityModelLayers.TRIDENT));
    }

    public void render(MagmaTridentEntity tridentEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        n1 = g;
        MagmaTridentEntityRenderer.tridentEntity = tridentEntity;
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevYaw, tridentEntity.getYaw()) - 90.0F));
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(g, tridentEntity.prevPitch, tridentEntity.getPitch()) + 90.0F));
        VertexConsumer vertexConsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumerProvider, model.getLayer(this.getTexture(tridentEntity)), false, tridentEntity.isEnchanted());
        model.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.pop();
        super.render(tridentEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    public static MagmaTridentEntityModel getModel() {
        return model;
    }
    public Identifier getTexture(MagmaTridentEntity tridentEntity) {
        return TEXTURE;
    }

    public static Quaternion getValue1() {
        return Vec3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(n1, tridentEntity.prevYaw, tridentEntity.getYaw()) - 90.0F);
    }

    public static Quaternion getValue2() {
        return Vec3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(n1, tridentEntity.prevPitch, tridentEntity.getPitch()) + 90.0F);
    }

}
