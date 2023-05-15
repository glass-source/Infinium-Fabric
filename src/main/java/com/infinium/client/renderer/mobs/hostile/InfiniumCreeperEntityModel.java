package com.infinium.client.renderer.mobs.hostile;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class InfiniumCreeperEntityModel<T extends Entity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart left_hind_leg;
	private final ModelPart right_hind_leg;
	private final ModelPart left_front_leg;
	private final ModelPart right_front_leg;
	public InfiniumCreeperEntityModel(ModelPart root) {
		this.root = root;
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.left_hind_leg = root.getChild("left_hind_leg");
		this.right_hind_leg = root.getChild("right_hind_leg");
		this.left_front_leg = root.getChild("left_front_leg");
		this.right_front_leg = root.getChild("right_front_leg");
	}

	public ModelPart getPart() {
		return this.root;
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));
		head.addChild("void_ghoul_head", ModelPartBuilder.create().uv(24, 0).cuboid(-3.0F, -24.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(24, 3).cuboid(1.0F, -24.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 0).cuboid(-1.0F, -22.0F, -5.0F, 2.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(30, 4).cuboid(1.0F, -21.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
				.uv(34, 4).cuboid(-2.0F, -21.0F, -5.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0F, 1.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.0F, 0.0F));

		body.addChild("ghoul_body", ModelPartBuilder.create().uv(42, 0).cuboid(1.0F, -14.0F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F))
				.uv(54, 5).cuboid(1.0F, -12.0F, -2.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(54, 11).cuboid(0.0F, -14.0F, -2.0F, 1.0F, 2.0F, 4.0F, new Dilation(0.0F))
				.uv(54, 17).cuboid(1.0F, -15.0F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(54, 22).cuboid(2.0F, -16.0F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(54, 27).cuboid(3.0F, -17.0F, -2.0F, 1.0F, 1.0F, 4.0F, new Dilation(0.0F))
				.uv(52, 0).cuboid(2.0F, -10.0F, -2.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0F, 0.0F));

		modelPartData.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 18.0F, 4.0F));

		modelPartData.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 18.0F, 4.0F));

		modelPartData.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 18.0F, -4.0F));

		modelPartData.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 18.0F, -4.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
		this.left_hind_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
		this.right_hind_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.left_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
		this.right_front_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_hind_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_hind_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		left_front_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		right_front_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}




}