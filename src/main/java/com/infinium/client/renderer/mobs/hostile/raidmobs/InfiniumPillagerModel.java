package com.infinium.client.renderer.mobs.hostile.raidmobs;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class InfiniumPillagerModel<T extends IllagerEntity> extends IllagerEntityModel<T> {


	public final ModelPart head;
	public final ModelPart hat;
	public final ModelPart hat2;

	public final ModelPart nose;
	public final ModelPart body;
	public final ModelPart left_arm;
	private final ModelPart arms;
	public final ModelPart right_arm;
	public final ModelPart left_leg;
	public final ModelPart right_leg;
	public InfiniumPillagerModel(ModelPart root) {
		super(root);
		this.head = root.getChild("head");
		this.hat = this.head.getChild("hat");
		this.hat2 = this.head.getChild("hat2");
		this.nose = this.head.getChild("nose");
		this.body = root.getChild("body");
		this.arms = root.getChild("arms");
		this.left_arm = root.getChild("left_arm");
		this.right_arm = root.getChild("right_arm");
		this.left_leg = root.getChild("left_leg");
		this.right_leg = root.getChild("right_leg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData root = modelData.getRoot();
		ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild("hat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		head.addChild("hat2", ModelPartBuilder.create().uv(32, 0).cuboid(-4.5F, -10.5F, -4.5F, 9.0F, 9.0F, 9.0F, new Dilation(-0.2F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
		root.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)).uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.5F, 0.3F));
		root.addChild("left_arm", ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
		root.addChild("right_arm", ModelPartBuilder.create().uv(40, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
		root.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		root.addChild("right_leg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 70, 64);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.arms.visible = entity.getState() == IllagerEntity.State.CROSSED;
		super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}

	private ModelPart getAttackingArm(Arm arm) {
		return arm == Arm.LEFT ? this.left_arm : this.right_arm;
	}

	public ModelPart getHat() {
		return this.hat;
	}

	public ModelPart getHead() {
		return this.head;
	}

	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.getAttackingArm(arm).rotate(matrices);
	}
}