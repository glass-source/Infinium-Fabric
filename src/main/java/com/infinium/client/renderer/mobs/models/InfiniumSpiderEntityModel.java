package com.infinium.client.renderer.mobs.models;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

public class InfiniumSpiderEntityModel<T extends Entity> extends SinglePartEntityModel<T> {

    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart custom;
    private final ModelPart leg1;
    private final ModelPart custom2;
    private final ModelPart bone25;
    private final ModelPart bone26;
    private final ModelPart leg2;
    private final ModelPart custom3;
    private final ModelPart bone22;
    private final ModelPart bone23;
    private final ModelPart leg3;
    private final ModelPart custom4;
    private final ModelPart bone19;
    private final ModelPart bone20;
    private final ModelPart leg4;
    private final ModelPart custom5;
    private final ModelPart bone16;
    private final ModelPart bone17;
    private final ModelPart leg5;
    private final ModelPart custom6;
    private final ModelPart bone13;
    private final ModelPart bone14;
    private final ModelPart leg6;
    private final ModelPart custom7;
    private final ModelPart bone10;
    private final ModelPart bone11;
    private final ModelPart leg7;
    private final ModelPart custom8;
    private final ModelPart bone7;
    private final ModelPart bone8;
    private final ModelPart leg8;
    private final ModelPart custom9;
    private final ModelPart bone4;
    private final ModelPart bone5;

    public InfiniumSpiderEntityModel(ModelPart root) {
        this.head = root.getChild("head");
        this.neck = root.getChild("neck");

        this.body = root.getChild("body");
        this.custom = body.getChild("custom");

        this.leg1 = root.getChild("leg1");
        this.custom2 = leg1.getChild("custom2");
        this.bone25 = custom2.getChild("bone25");
        this.bone26 = bone25.getChild("bone26");

        this.leg2 = root.getChild("leg2");
        this.custom3 = leg2.getChild("custom3");
        this.bone22 = custom3.getChild("bone22");
        this.bone23 = bone22.getChild("bone23");

        this.leg3 = root.getChild("leg3");
        this.custom4 = leg3.getChild("custom4");
        this.bone19 = custom4.getChild("bone19");
        this.bone20 = bone19.getChild("bone20");

        this.leg4 = root.getChild("leg4");
        this.custom5 = leg4.getChild("custom5");
        this.bone16 = custom5.getChild("bone16");
        this.bone17 = bone16.getChild("bone17");

        this.leg5 = root.getChild("leg5");
        this.custom6 = leg5.getChild("custom6");
        this.bone13 = custom6.getChild("bone13");
        this.bone14 = bone13.getChild("bone14");

        this.leg6 = root.getChild("leg6");
        this.custom7 = leg6.getChild("custom7");
        this.bone10 = custom7.getChild("bone10");
        this.bone11 = bone10.getChild("bone11");

        this.leg7 = root.getChild("leg7");
        this.custom8 = leg7.getChild("custom8");
        this.bone7 = custom8.getChild("bone7");
        this.bone8 = bone7.getChild("bone8");

        this.leg8 = root.getChild("leg8");
        this.custom9 = leg8.getChild("custom9");
        this.bone4 = custom9.getChild("bone4");
        this.bone5 = bone4.getChild("bone5");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(32, 4).cuboid(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, -3.0F));
        ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, 0.0F));
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 12).cuboid(-5.0F, -4.0F, -6.0F, 10.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, 9.0F));
        ModelPartData custom = body.addChild("custom", ModelPartBuilder.create().uv(0, 32).cuboid(-5.0F, -5.0F, 0.0F, 10.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -6.0F, 0.2618F, 0.0F, 0.0F));
        ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(18, 0).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 15.0F, 4.0F));
        ModelPartData custom2 = leg1.addChild("custom2", ModelPartBuilder.create().uv(1, 53).cuboid(-8.5F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 0.0F, 0.0F, -0.1626F, 0.5426F, 1.0447F));
        ModelPartData bone25 = custom2.addChild("bone25", ModelPartBuilder.create().uv(9, 53).cuboid(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-8.5F, -1.0F, 0.0F, 0.0F, 0.0F, -1.0472F));
        ModelPartData bone26 = bone25.addChild("bone26", ModelPartBuilder.create().uv(11, 53).cuboid(-10.4324F, -0.441F, -1.0036F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0472F));
        ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create().uv(18, 0).cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 15.0F, 4.0F));
        ModelPartData custom3 = leg2.addChild("custom3", ModelPartBuilder.create().uv(3, 53).cuboid(-2.5F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 0.0F, 0.0F, -0.1626F, -0.5426F, -1.0447F));
        ModelPartData bone22 = custom3.addChild("bone22", ModelPartBuilder.create().uv(7, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(8.5F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0472F));
        ModelPartData bone23 = bone22.addChild("bone23", ModelPartBuilder.create().uv(12, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0472F));
        ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create().uv(18, 0).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 15.0F, 1.0F));
        ModelPartData custom4 = leg3.addChild("custom4", ModelPartBuilder.create().uv(13, 53).cuboid(-10.0F, -1.0F, 2.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, -3.0F, -0.2651F, 0.0287F, 1.2191F));
        ModelPartData bone19 = custom4.addChild("bone19", ModelPartBuilder.create().uv(12, 53).cuboid(-10.0F, 0.0F, 2.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.2654F));
        ModelPartData bone20 = bone19.addChild("bone20", ModelPartBuilder.create().uv(0, 53).cuboid(-10.0F, 0.0F, 2.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        ModelPartData leg4 = modelPartData.addChild("leg4", ModelPartBuilder.create().uv(18, 0).cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 15.0F, 1.0F));
        ModelPartData custom5 = leg4.addChild("custom5", ModelPartBuilder.create().uv(3, 53).cuboid(-1.0F, -1.0F, 2.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, -3.0F, -0.2651F, 0.0287F, -1.2191F));
        ModelPartData bone16 = custom5.addChild("bone16", ModelPartBuilder.create().uv(11, 53).cuboid(0.0F, 0.0F, 2.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.2654F));
        ModelPartData bone17 = bone16.addChild("bone17", ModelPartBuilder.create().uv(4, 53).cuboid(0.0F, 0.0F, 2.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        ModelPartData leg5 = modelPartData.addChild("leg5", ModelPartBuilder.create().uv(18, 0).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 15.0F, -2.0F));
        ModelPartData custom6 = leg5.addChild("custom6", ModelPartBuilder.create().uv(8, 53).cuboid(-10.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 0.0F, 0.0F, 0.0F, -0.1381F, 1.1117F));
        ModelPartData bone13 = custom6.addChild("bone13", ModelPartBuilder.create().uv(7, 53).cuboid(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.2217F));
        ModelPartData bone14 = bone13.addChild("bone14", ModelPartBuilder.create().uv(12, 53).cuboid(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.0908F));
        ModelPartData leg6 = modelPartData.addChild("leg6", ModelPartBuilder.create().uv(18, 0).cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 15.0F, -2.0F));
        ModelPartData custom7 = leg6.addChild("custom7", ModelPartBuilder.create().uv(1, 53).cuboid(-1.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 0.0F, 0.0F, 0.0F, 0.1381F, -1.1117F));
        ModelPartData bone10 = custom7.addChild("bone10", ModelPartBuilder.create().uv(3, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.2217F));
        ModelPartData bone11 = bone10.addChild("bone11", ModelPartBuilder.create().uv(10, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0908F));
        ModelPartData leg7 = modelPartData.addChild("leg7", ModelPartBuilder.create().uv(18, 0).cuboid(-15.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 15.0F, -5.0F));
        ModelPartData custom8 = leg7.addChild("custom8", ModelPartBuilder.create().uv(2, 53).cuboid(-9.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.0739F, -0.5102F, 1.1596F));
        ModelPartData bone7 = custom8.addChild("bone7", ModelPartBuilder.create().uv(10, 53).cuboid(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, -1.0F, 0.0F, 0.0F, 0.0F, -1.3526F));
        ModelPartData bone8 = bone7.addChild("bone8", ModelPartBuilder.create().uv(7, 53).cuboid(-10.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));
        ModelPartData leg8 = modelPartData.addChild("leg8", ModelPartBuilder.create().uv(18, 0).cuboid(-1.0F, -1.0F, -1.0F, 16.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 15.0F, -5.0F));
        ModelPartData custom9 = leg8.addChild("custom9", ModelPartBuilder.create().uv(7, 53).cuboid(-2.0F, -1.0F, -1.0F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0739F, 0.5102F, -1.1596F));
        ModelPartData bone4 = custom9.addChild("bone4", ModelPartBuilder.create().uv(6, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.3526F));
        ModelPartData bone5 = bone4.addChild("bone5", ModelPartBuilder.create().uv(4, 53).cuboid(0.0F, 0.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7418F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.body;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;



    }
}
