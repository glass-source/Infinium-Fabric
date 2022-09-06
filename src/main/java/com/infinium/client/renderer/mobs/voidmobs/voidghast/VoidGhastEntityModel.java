package com.infinium.client.renderer.mobs.voidmobs.voidghast;

import com.infinium.server.entities.mobs.voidmobs.voidghast.VoidGhastEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class VoidGhastEntityModel extends EntityModel<VoidGhastEntity> {

    private final ModelPart body;
	private final ModelPart[] tentacles = new ModelPart[9];

    public VoidGhastEntityModel(ModelPart root) {
        this.body = root.getChild("body");

		for(int i = 0; i < this.tentacles.length; ++i) {
			this.tentacles[i] = root.getChild(getTentacleName(i));
		}
    }

	private static String getTentacleName(int index) {
		return "tentacle" + index;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 4.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
		ModelPartData face1 = body.addChild("face1", ModelPartBuilder.create().uv(8, 37).cuboid(-3.5F, -6.5F + 12, -7.0F, 5.0F, 13.0F, 14.0F, new Dilation(0.1F)), ModelTransform.of(9.5F, 1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));
		ModelPartData face2 = body.addChild("face2", ModelPartBuilder.create().uv(32, 32).cuboid(-12F, -5.0F + 12.75f, -2.0F, 8.0F, 7.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.4441F, -7.7756F, 2.0F, -0.1074F, 0.2865F, -0.6264F));
		ModelPartData face3 = body.addChild("face3", ModelPartBuilder.create().uv(0, 32).cuboid(0.0F, -3.0F + 12, -1.75F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-7.25F, -5.5F, -3.75F, 0.0F, -0.3054F, 0.3054F));
		Random random = new Random(1660L);

		for(int i = 0; i < 9; ++i) {
			float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
			float g = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
			int j = random.nextInt(7) + 8;
			modelPartData.addChild(getTentacleName(i), ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0f, -1.0F, 2.0F, (float)j, 2.0F), ModelTransform.pivot(f, 24.6F, g));
		}
		return TexturedModelData.of(modelData, 64, 64);
	}

    @Override
    public void setAngles(VoidGhastEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		for(int i = 0; i < this.tentacles.length; ++i) {
			this.tentacles[i].pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)i) + 0.4F;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		for (ModelPart part : tentacles) {
			part.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		}
	}

}