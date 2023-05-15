package com.infinium.client.renderer.mobs.hostile.ghoulmobs.ghoulwitch;

import com.infinium.server.entities.mobs.hostile.ghoulmobs.ghoulwitch.GhoulWitchEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
@Environment(EnvType.CLIENT)
public class GhoulWitchEntityModel extends EntityModel<GhoulWitchEntity> {
	private final ModelPart head;
	private final ModelPart hat;
	private final ModelPart hat2;
	private final ModelPart nose;
	private final ModelPart mole;
	private final ModelPart body;
	private final ModelPart bodywear;
	private final ModelPart arms;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	public GhoulWitchEntityModel(ModelPart root) {
		this.head = root.getChild("head");
		this.hat = head.getChild("hat");
		this.hat2 = hat.getChild("hat2");
		this.nose = head.getChild("nose");
		this.mole = root.getChild("mole");
		this.body = root.getChild("body");
		this.bodywear = root.getChild("bodywear");
		this.arms = root.getChild("arms");
		this.leftLeg = root.getChild("leftLeg");
		this.rightLeg = root.getChild("rightLeg");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.25F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.25F, 0.0F));
		ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(0, 64).cuboid(0.0F, -1.0F, 0.0F, 10.0F, 3.0F, 10.0F, new Dilation(0.0F)).uv(0, 64).cuboid(-1.0F, 0.0F, -1.0F, 12.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -13.55F, -5.0F));
		ModelPartData hat2 = hat.addChild("hat2", ModelPartBuilder.create(), ModelTransform.of(1.75F, -4.0F, 2.0F, -0.0524F, 0.0F, 0.0262F));
		ModelPartData hat3 = hat2.addChild("hat3", ModelPartBuilder.create(), ModelTransform.of(1.75F, -4.0F, 2.0F, -0.1047F, 0.0F, 0.0524F));
		hat3.addChild("hat4", ModelPartBuilder.create(), ModelTransform.of(1.75F, -2.0F, 2.0F, -0.2094F, 0.0F, 0.1047F));
		modelPartData.addChild("headwear2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(46, 56).cuboid(-2.0F, -4.7779F, 1.1439F, 4.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -7.0F, 0.3927F, 0.0F, 0.0F));
		nose.addChild("bone", ModelPartBuilder.create().uv(50, 69).cuboid(-1.0F, 2.505F, -3.2953F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.7779F, 1.1439F, 0.1309F, 0.0F, 0.0F));
		modelPartData.addChild("mole", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 3.75F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		modelPartData.addChild("bodywear", ModelPartBuilder.create().uv(0, 38).cuboid(-4.0F, -1.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.5F, 0.3F));
		ModelPartData arms_rotation = arms.addChild("arms_rotation", ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)).uv(40, 38).cuboid(-4.0F, 4.0F, -2.0F, 8.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -4.5F, 0.05F, -0.7505F, 0.0F, 0.0F));
		arms_rotation.addChild("arms_flipped", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -24.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		modelPartData.addChild("leftLeg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).uv(25, 31).cuboid(-2.0F, 11.0F, -3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		ModelPartData rightLeg = modelPartData.addChild("rightLeg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
		rightLeg.addChild("rightLeg_sub_0", ModelPartBuilder.create().uv(27, 36).mirrored().cuboid(-4.0F, -1.0F, -3.0F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 128);
	}
	@Override
	public void setAngles(GhoulWitchEntity entity, float limbAngle, float limbDistance, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.yaw = netHeadYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
		this.hat.setPivot(-5.0F, -10.55F, -5.0F);
		float f = 0.01F * (float)(entity.getId() % 10);
		this.nose.pitch = MathHelper.sin((float)entity.age * f) * 4.5F * 0.017453292F;
		this.nose.yaw = 0.0F;
		this.nose.roll = MathHelper.cos((float)entity.age * f) * 2.5F * 0.017453292F;

		this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 0.5F;
		this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 0.5F;
		this.rightLeg.yaw = 0.0F;
		this.leftLeg.yaw = 0.0F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		bodywear.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		mole.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		arms.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightLeg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	public ModelPart getHead() {
		return head;
	}
}