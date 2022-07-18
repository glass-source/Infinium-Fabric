package com.infinium.client.renderer.voidghast;

import com.infinium.global.entities.mobs.voidmobs.VoidGhastEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.FlyingEntity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class VoidGhastEntityModel<T extends Entity> extends EntityModel<VoidGhastEntity> {
    private final ModelPart body;
    private final ModelPart tentacle1;
    private final ModelPart tentacle2;
    private final ModelPart tentacle3;
    private final ModelPart tentacle4;
    private final ModelPart tentacle5;
    private final ModelPart tentacle6;
    private final ModelPart tentacle7;
    private final ModelPart tentacle8;
   // private final ModelPart tentacle9;

    public VoidGhastEntityModel(ModelPart root) {
        this.body = root.getChild("body");
        this.tentacle1 = root.getChild("tentacle1");
        this.tentacle2 = root.getChild("tentacle2");
        this.tentacle3 = root.getChild("tentacle3");
        this.tentacle4 = root.getChild("tentacle4");
        this.tentacle5 = root.getChild("tentacle5");
        this.tentacle6 = root.getChild("tentacle6");
        this.tentacle7 = root.getChild("tentacle7");
        this.tentacle8 = root.getChild("tentacle8");
        //this.tentacle9 = root.getChild("tentacle9");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 4.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
        ModelPartData face1 = body.addChild("face1", ModelPartBuilder.create().uv(8, 37).cuboid(-3.5F, 5.5F, -7.0F, 5.0F, 13.0F, 14.0F, new Dilation(0.1F)), ModelTransform.of(9.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
        ModelPartData face2 = body.addChild("face2", ModelPartBuilder.create().uv(32, 32).cuboid(-6.75F, 7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.4441F, -7.7756F, 2.0F, -0.1074F, 0.2865F, -0.6264F));
        ModelPartData bone = face2.addChild("bone", ModelPartBuilder.create().uv(0, 8).cuboid(0.25F, 9.5F, 1.75F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.75F, 3.75F, 0.0F, 0.3244F, -0.1313F, -0.7633F));

        ModelPartData bone2 = face2.addChild("bone2", ModelPartBuilder.create().uv(16, 9).cuboid(0.25F, 9.5F, -1.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.1F)), ModelTransform.of(0.75F, 3.75F, -2.0F, -0.1065F, -0.1731F, -0.7162F));

        ModelPartData face3 = body.addChild("face3", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, 9.0F, -1.75F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.25F, -5.5F, -3.75F, 0.0F, -0.3054F, 0.3054F));

        ModelPartData bone3 = face3.addChild("bone3", ModelPartBuilder.create().uv(12, 38).cuboid(-1.0F, 10.5F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F)), ModelTransform.of(-0.75F, 3.75F, 2.75F, 0.0F, 0.0F, 0.2618F));

        ModelPartData bone4 = face3.addChild("bone4", ModelPartBuilder.create().uv(7, 38).cuboid(-1.0F, 10.5F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(-0.3F)), ModelTransform.of(-0.75F, 3.75F, 0.0F, -0.3927F, 0.0F, 0.2618F));

        ModelPartData tentacle1 = modelPartData.addChild("tentacle1", ModelPartBuilder.create().uv(0, 0).cuboid(-8.4F, 12.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.7F, 11.0F, -5.0F));

        ModelPartData tentacle2 = modelPartData.addChild("tentacle2", ModelPartBuilder.create().uv(0, 0).cuboid(6.6F, 12.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.3F, 11.0F, -5.0F));

        ModelPartData tentacle3 = modelPartData.addChild("tentacle3", ModelPartBuilder.create().uv(0, 0).cuboid(6.6F, 12.0F, -1.0F, 2.0F, 13.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.3F, 11.0F, -5.0F));

        ModelPartData tentacle4 = modelPartData.addChild("tentacle4", ModelPartBuilder.create().uv(0, 0).cuboid(-13.6F, 12.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(6.3F, 11.0F, 0.0F));

        ModelPartData tentacle5 = modelPartData.addChild("tentacle5", ModelPartBuilder.create().uv(0, 0).cuboid(-3.6F, 12.0F, -1.0F, 2.0F, 11.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.3F, 11.0F, 0.0F));

        ModelPartData tentacle6 = modelPartData.addChild("tentacle6", ModelPartBuilder.create().uv(0, 0).cuboid(6.4F, 12.0F, -1.0F, 2.0F, 10.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.7F, 11.0F, 0.0F));

        ModelPartData tentacle7 = modelPartData.addChild("tentacle7", ModelPartBuilder.create().uv(0, 0).cuboid(-8.4F, 12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.7F, 11.0F, 5.0F));

        ModelPartData tentacle8 = modelPartData.addChild("tentacle8", ModelPartBuilder.create().uv(0, 0).cuboid(6.6F, 12.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.3F, 11.0F, 5.0F));

        ModelPartData tentacle9 = modelPartData.addChild("tentacle9", ModelPartBuilder.create().uv(0, 0).cuboid(6.6F, 12.0F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.3F, 11.0F, 5.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        tentacle8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
       // tentacle9.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(VoidGhastEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.tentacle1.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)0) + 0.4F;
        this.tentacle2.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)1) + 0.4F;
        this.tentacle3.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)2) + 0.4F;
        this.tentacle4.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)3) + 0.4F;
        this.tentacle5.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)4) + 0.4F;
        this.tentacle6.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)5) + 0.4F;
        this.tentacle7.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)6) + 0.4F;
        this.tentacle8.pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)7) + 0.4F;
    }

}