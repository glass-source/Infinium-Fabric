package com.infinium.client.renderer.mobs.hostile;

import com.infinium.Infinium;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@Environment(EnvType.CLIENT)
public class InfiniumSkeletonEntityModel <T extends MobEntity & RangedAttackMob> extends BipedEntityModel<T> {

	public static final EntityModelLayer INFINIUM_SKELETON = new EntityModelLayer(new Identifier(Infinium.MOD_ID, "infinium_skeleton"), "infinium_skeleton");
	public InfiniumSkeletonEntityModel(ModelPart root) {
		super(root);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		head.addChild("void_head", ModelPartBuilder.create().uv(0, 43).cuboid(-3.0F, -30.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 43).cuboid(1.0F, -30.0F, -5.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(10, 47).cuboid(-1.0F, -28.0F, -5.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 46).cuboid(-2.0F, -27.0F, -5.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 1.0F));

		modelPartData.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 16).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body.addChild("pirate_skeleton_body", ModelPartBuilder.create().uv(40, 80).cuboid(-4.0F, -24.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData void_body = body.addChild("void_body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData main_bone = void_body.addChild("main_bone", ModelPartBuilder.create().uv(0, 34).cuboid(-1.5F, -0.1086F, -2.1305F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(7, 61).cuboid(-1.5F, 1.8914F, -1.1305F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 59).cuboid(-0.5F, 1.8914F, -2.1305F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		ModelPartData bone = main_bone.addChild("bone", ModelPartBuilder.create().uv(0, 34).cuboid(-1.5F, -0.1386F, -0.098F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(7, 61).cuboid(-1.5F, 1.8614F, 0.902F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 59).cuboid(-0.5F, 1.8614F, -0.098F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 6.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

		bone.addChild("bone2", ModelPartBuilder.create().uv(0, 34).cuboid(-1.5F, 0.6034F, -0.7064F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 59).cuboid(-0.5F, 2.6034F, -0.7064F, 1.0F, 2.0F, 3.0F, new Dilation(0.0F))
		.uv(7, 61).cuboid(-1.5F, 2.6034F, 0.2936F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 5.0F, 0.25F, 0.3491F, 0.0F, 0.0F));

		void_body.addChild("c_bone", ModelPartBuilder.create().uv(8, 30).cuboid(-4.0F, 0.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(38, 30).cuboid(-4.0F, 0.0F, -2.25F, 8.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(40, 16).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

		ModelPartData pirate_skeleton_left_arm = left_arm.addChild("pirate_skeleton_left_arm", ModelPartBuilder.create(), ModelTransform.pivot(-10.0F, 0.0F, 0.0F));

		pirate_skeleton_left_arm.addChild("left_arm_sub_1", ModelPartBuilder.create().uv(24, 80).mirrored().cuboid(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(8, 80).mirrored().cuboid(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 16).cuboid(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

		right_arm.addChild("pirate_skeleton_right_arm", ModelPartBuilder.create().uv(24, 80).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(8, 80).cuboid(-8.0F, -24.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(5.0F, 22.0F, 0.0F));

		right_arm.addChild("void_right_arm", ModelPartBuilder.create().uv(48, 16).cuboid(-7.25F, -24.25F, -1.5F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 22.0F, 0.0F));

		ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 16).mirrored().cuboid(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 12.0F, 0.1F));

		ModelPartData pirate_skeleton_left_leg = left_leg.addChild("pirate_skeleton_left_leg", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, 12.0F, -0.1F));

		pirate_skeleton_left_leg.addChild("left_leg_sub_1", ModelPartBuilder.create().uv(48, 64).mirrored().cuboid(0.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
		.uv(32, 71).mirrored().cuboid(0.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.25F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, 0.0F, -1.1F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.1F));

		right_leg.addChild("pirate_skeleton_right_leg", ModelPartBuilder.create().uv(48, 64).cuboid(-4.0F, -12.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F))
		.uv(32, 71).cuboid(-4.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.25F)), ModelTransform.pivot(2.0F, 12.0F, -0.1F));
		return TexturedModelData.of(modelData, 64, 96);
	}
	
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		this.hat.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightArm.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	public void animateModel(T mobEntity, float f, float g, float h) {
		this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
		this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
		ItemStack itemStack = mobEntity.getStackInHand(Hand.MAIN_HAND);
		if (itemStack.isOf(Items.BOW) && mobEntity.isAttacking()) {
			if (mobEntity.getMainArm() == Arm.RIGHT) {
				this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
			} else {
				this.leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
			}
		}

		super.animateModel(mobEntity, f, g, h);
	}

	public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
		super.setAngles(mobEntity, f, g, h, i, j);
		ItemStack itemStack = mobEntity.getMainHandStack();
		if (mobEntity.isAttacking() && (itemStack.isEmpty() || !itemStack.isOf(Items.BOW))) {
			float k = MathHelper.sin(this.handSwingProgress * 3.1415927F);
			float l = MathHelper.sin((1.0F - (1.0F - this.handSwingProgress) * (1.0F - this.handSwingProgress)) * 3.1415927F);
			this.rightArm.roll = 0.0F;
			this.leftArm.roll = 0.0F;
			this.rightArm.yaw = -(0.1F - k * 0.6F);
			this.leftArm.yaw = 0.1F - k * 0.6F;
			this.rightArm.pitch = -1.5707964F;
			this.leftArm.pitch = -1.5707964F;
			ModelPart var10000 = this.rightArm;
			var10000.pitch -= k * 1.2F - l * 0.4F;
			var10000 = this.leftArm;
			var10000.pitch -= k * 1.2F - l * 0.4F;
			CrossbowPosing.swingArms(this.rightArm, this.leftArm, h);
		}

	}

	public void setArmAngle(Arm arm, MatrixStack matrices) {
		float f = arm == Arm.RIGHT ? 1.0F : -1.0F;
		ModelPart modelPart = this.getArm(arm);
		modelPart.pivotX += f;
		modelPart.rotate(matrices);
		modelPart.pivotX -= f;
	}
}