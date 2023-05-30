package com.infinium.client.renderer.mobs.hostile.nightmare.nightmareblaze;

import com.infinium.server.entities.mobs.hostile.nightmare.NightmareBlazeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class NightmareBlazeEntityModel extends EntityModel<NightmareBlazeEntity> {
	private final ModelPart head;
	private final ModelPart nightmare_helmet;
	private final ModelPart right_helmet;
	private final ModelPart head_sub_2;
	private final ModelPart left_helmet;
	private final ModelPart head_sub_4;
	private final ModelPart middle_helmet;
	private final ModelPart stick1;
	private final ModelPart stick2;
	private final ModelPart stick3;
	private final ModelPart stick4;
	private final ModelPart stick5;
	private final ModelPart nightmare_stick5;
	private final ModelPart stick6;
	private final ModelPart nightmare_stick6;
	private final ModelPart stick6_sub_1;
	private final ModelPart stick7;
	private final ModelPart nightmare_stick7;
	private final ModelPart stick8;
	private final ModelPart nightmare_stick8;
	private final ModelPart stick8_sub_1;
	private final ModelPart stick9;
	private final ModelPart nightmare_stick9;
	private final ModelPart stick9_sub_1;
	private final ModelPart stick10;
	private final ModelPart nightmare_stick10;
	private final ModelPart stick10_sub_1;
	private final ModelPart stick11;
	private final ModelPart nightmare_stick11;
	private final ModelPart stick12;
	private final ModelPart nightmare_stick12;
	private final ModelPart[] rods;
	public NightmareBlazeEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.nightmare_helmet = head.getChild("nightmare_helmet");
		this.right_helmet = nightmare_helmet.getChild("right_helmet");
		this.head_sub_2 = right_helmet.getChild("head_sub_2");
		this.left_helmet = nightmare_helmet.getChild("left_helmet");
		this.head_sub_4 = left_helmet.getChild("head_sub_4");
		this.middle_helmet = nightmare_helmet.getChild("middle_helmet");


		this.stick1 = root.getChild("stick1");
		this.stick2 = root.getChild("stick2");
		this.stick3 = root.getChild("stick3");
		this.stick4 = root.getChild("stick4");

		this.stick5 = root.getChild("stick5");
		this.nightmare_stick5 = stick5.getChild("nightmare_stick5");

		this.stick6 = root.getChild("stick6");
		this.nightmare_stick6 = stick6.getChild("nightmare_stick6");
		this.stick6_sub_1 = nightmare_stick6.getChild("stick6_sub_1");

		this.stick7 = root.getChild("stick7");
		this.nightmare_stick7 = stick7.getChild("nightmare_stick7");

		this.stick8 = root.getChild("stick8");
		this.nightmare_stick8 = stick8.getChild("nightmare_stick8");
		this.stick8_sub_1 = nightmare_stick8.getChild("stick8_sub_1");

		this.stick9 = root.getChild("stick9");
		this.nightmare_stick9 = stick9.getChild("nightmare_stick9");
		this.stick9_sub_1 = nightmare_stick9.getChild("stick9_sub_1");

		this.stick10 = root.getChild("stick10");
		this.nightmare_stick10 = stick10.getChild("nightmare_stick10");
		this.stick10_sub_1 = nightmare_stick10.getChild("stick10_sub_1");

		this.stick11 = root.getChild("stick11");
		this.nightmare_stick11 = stick11.getChild("nightmare_stick11");

		this.stick12 = root.getChild("stick12");
		this.nightmare_stick12 = stick12.getChild("nightmare_stick12");

		this.rods = new ModelPart[] {
				stick1,
				stick2,
				stick3,
				stick4,
				stick5,
				stick6,
				stick7,
				stick8,
				stick9,
				stick10,
				stick11,
				stick12,
		};
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head;
		head = modelPartData.addChild("head", ModelPartBuilder.create().uv(95, 64).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData nightmare_helmet = head.addChild("nightmare_helmet", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 32.0F, 15.0F));

		ModelPartData right_helmet = nightmare_helmet.addChild("right_helmet", ModelPartBuilder.create().uv(123, 80).cuboid(-3.0F, -32.0F, -5.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
		.uv(123, 86).cuboid(-4.0F, -41.0F, -5.0F, 1.0F, 14.0F, 1.0F, new Dilation(0.0F))
		.uv(119, 80).cuboid(-3.0F, -35.0F, -5.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(119, 83).cuboid(-2.0F, -36.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(113, 94).cuboid(-5.0F, -30.0F, -2.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(105, 80).cuboid(-5.0F, -34.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -15.0F));

		right_helmet.addChild("head_sub_2", ModelPartBuilder.create().uv(105, 83).mirrored().cuboid(-5.0F, -37.0F, 4.0F, 2.0F, 9.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(107, 93).mirrored().cuboid(-5.0F, -37.0F, -5.0F, 1.0F, 9.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(101, 80).mirrored().cuboid(-5.0F, -37.0F, 2.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(101, 90).mirrored().cuboid(-5.0F, -38.0F, 3.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(101, 101).mirrored().cuboid(-3.0F, -36.0F, 4.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(116, 100).mirrored().cuboid(-5.0F, -36.0F, -3.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
		.uv(113, 88).mirrored().cuboid(-5.0F, -34.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(111, 91).mirrored().cuboid(-5.0F, -32.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_helmet = nightmare_helmet.addChild("left_helmet", ModelPartBuilder.create().uv(105, 83).cuboid(3.0F, -37.0F, 4.0F, 2.0F, 9.0F, 1.0F, new Dilation(0.0F))
		.uv(107, 93).cuboid(4.0F, -37.0F, -5.0F, 1.0F, 9.0F, 2.0F, new Dilation(0.0F))
		.uv(101, 80).cuboid(4.0F, -37.0F, 2.0F, 1.0F, 9.0F, 1.0F, new Dilation(0.0F))
		.uv(101, 90).cuboid(4.0F, -38.0F, 3.0F, 1.0F, 10.0F, 1.0F, new Dilation(0.0F))
		.uv(101, 101).cuboid(1.0F, -36.0F, 4.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
		.uv(116, 100).cuboid(4.0F, -36.0F, -3.0F, 1.0F, 1.0F, 5.0F, new Dilation(0.0F))
		.uv(113, 88).cuboid(4.0F, -34.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(111, 91).cuboid(4.0F, -32.0F, -1.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -15.0F));

		left_helmet.addChild("head_sub_4", ModelPartBuilder.create().uv(123, 80).mirrored().cuboid(2.0F, -32.0F, -5.0F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(123, 86).mirrored().cuboid(3.0F, -41.0F, -5.0F, 1.0F, 14.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(119, 80).mirrored().cuboid(2.0F, -35.0F, -5.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(119, 83).mirrored().cuboid(1.0F, -36.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(113, 94).mirrored().cuboid(4.0F, -30.0F, -2.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(105, 80).mirrored().cuboid(4.0F, -34.0F, 0.0F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		nightmare_helmet.addChild("middle_helmet", ModelPartBuilder.create().uv(117, 87).cuboid(-1.0F, -39.0F, -5.0F, 2.0F, 6.0F, 1.0F, new Dilation(0.0F))
		.uv(111, 80).cuboid(-1.0F, -37.0F, 4.0F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -15.0F));

		modelPartData.addChild("stick1", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, -2.0F, -7.0F));

		modelPartData.addChild("stick2", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, -2.0F, -7.0F));

		modelPartData.addChild("stick3", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(7.0F, -2.0F, 7.0F));

		modelPartData.addChild("stick4", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, -2.0F, 7.0F));

		ModelPartData stick5 = modelPartData.addChild("stick5", ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 2.0F, -5.0F));

		stick5.addChild("nightmare_stick5", ModelPartBuilder.create().uv(16, 58).mirrored().cuboid(-4.0F, -8.0F, -4.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.0F, 22.0F, 5.0F));

		ModelPartData stick6 = modelPartData.addChild("stick6", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, -5.0F));

		ModelPartData nightmare_stick6 = stick6.addChild("nightmare_stick6", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, 22.0F, 5.0F));

		nightmare_stick6.addChild("stick6_sub_1", ModelPartBuilder.create().uv(16, 58).mirrored().cuboid(2.0F, -8.0F, -4.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick7 = modelPartData.addChild("stick7", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 2.0F, 5.0F));

		stick7.addChild("nightmare_stick7", ModelPartBuilder.create().uv(16, 58).mirrored().cuboid(2.0F, -8.0F, 2.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.0F, 22.0F, -5.0F));

		ModelPartData stick8 = modelPartData.addChild("stick8", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 5.0F));

		ModelPartData nightmare_stick8 = stick8.addChild("nightmare_stick8", ModelPartBuilder.create(), ModelTransform.pivot(10.0F, 0.0F, 0.0F));

		nightmare_stick8.addChild("stick8_sub_1", ModelPartBuilder.create().uv(16, 58).mirrored().cuboid(-9.0F, 14.0F, -3.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick9 = modelPartData.addChild("stick9", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 10.0F, -3.0F));

		ModelPartData nightmare_stick9 = stick9.addChild("nightmare_stick9", ModelPartBuilder.create(), ModelTransform.of(3.0F, -6.0F, 3.0F, -0.0436F, 0.0F, 0.0F));

		nightmare_stick9.addChild("stick9_sub_1", ModelPartBuilder.create().uv(34, 0).mirrored().cuboid(-5.5453F, -14.0634F, -13.759F, 10.0F, 28.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick10 = modelPartData.addChild("stick10", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 10.0F, -3.0F));

		ModelPartData nightmare_stick10 = stick10.addChild("nightmare_stick10", ModelPartBuilder.create(), ModelTransform.of(-2.0F, -6.0F, 3.0F, 0.0F, 0.0F, -0.0436F));

		nightmare_stick10.addChild("stick10_sub_1", ModelPartBuilder.create().uv(22, 22).mirrored().cuboid(11.4478F, -14.0889F, -4.5118F, 1.0F, 28.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData stick11 = modelPartData.addChild("stick11", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.0F, 10.0F, 3.0F));

		stick11.addChild("nightmare_stick11", ModelPartBuilder.create().uv(44, 44).cuboid(-5.4996F, -14.0017F, 13.7074F, 10.0F, 28.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, -6.0F, -3.0F, 0.0436F, 0.0F, 0.0F));

		ModelPartData stick12 = modelPartData.addChild("stick12", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 10.0F, 3.0F));

		stick12.addChild("nightmare_stick12", ModelPartBuilder.create().uv(22, 22).cuboid(-14.8236F, -14.0711F, -4.4547F, 1.0F, 28.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -6.0F, -3.0F, 0.0F, 0.0F, 0.0436F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(NightmareBlazeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		float f = animationProgress * 3.1415927F * -0.1F;

		int i;
		for(i = 0; i < 4; ++i) {
			this.rods[i].pivotY = -2.0F + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25F);
			this.rods[i].pivotX = MathHelper.cos(f) * 9.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 9.0F;
			++f;
		}

		f = 0.7853982F + animationProgress * 3.1415927F * 0.03F;

		for(i = 4; i < 8; ++i) {
			this.rods[i].pivotY = 2.0F + MathHelper.cos(((float)(i * 2) + animationProgress) * 0.25F);
			this.rods[i].pivotX = MathHelper.cos(f) * 7.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 7.0F;
			++f;
		}

		f = 0.47123894F + animationProgress * 3.1415927F * -0.05F;

		for(i = 8; i < 12; ++i) {
			this.rods[i].pivotY = 11.0F + MathHelper.cos(((float)i * 1.5F + animationProgress) * 0.5F);
			this.rods[i].pivotX = MathHelper.cos(f) * 5.0F;
			this.rods[i].pivotZ = MathHelper.sin(f) * 5.0F;
			++f;
		}

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick9.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick10.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick11.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		stick12.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}