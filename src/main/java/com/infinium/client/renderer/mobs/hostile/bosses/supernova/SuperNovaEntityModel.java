package com.infinium.client.renderer.mobs.hostile.bosses.supernova;

import com.infinium.server.entities.mobs.hostile.bosses.SuperNovaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.6.0
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

@Environment(EnvType.CLIENT)
public class SuperNovaEntityModel <T extends SuperNovaEntity> extends SinglePartEntityModel<T> {
	private final ModelPart root;
	private final ModelPart ribcage;
	private final ModelPart tail;
	private final ModelPart centerHead;
	private final ModelPart rightHead;
	private final ModelPart leftHead;

	public SuperNovaEntityModel(ModelPart root) {
		this.root = root;
		this.ribcage = root.getChild("body2");
		this.tail = root.getChild("body3");
		this.centerHead = root.getChild("head1");
		this.rightHead = root.getChild("head2");
		this.leftHead = root.getChild("head3");
	}

	public ModelPart getPart() {
		return this.root;
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body1 = modelPartData.addChild("body1", ModelPartBuilder.create().uv(0, 84).cuboid(-10.0F, 3.9F, -0.5F, 20.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(36, 47).cuboid(-2.0F, 3.9F, -0.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-12.0F, 5.9F, -2.5F, 23.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		body1.addChild("right_neck", ModelPartBuilder.create().uv(36, 28).cuboid(-1.0F, -1.0F, 0.0F, 10.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-9.0F, 5.9F, 0.6F, -0.0594F, -0.0373F, 0.6806F));

		body1.addChild("left_neck", ModelPartBuilder.create().uv(36, 25).cuboid(-10.0F, -1.0F, 0.0F, 10.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, 4.9F, 0.6F, -0.0594F, 0.0373F, -0.6806F));

		ModelPartData arm_right = body1.addChild("arm_right", ModelPartBuilder.create().uv(24, 47).cuboid(-3.9676F, -3.9301F, -3.7389F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F))
		.uv(19, 25).cuboid(-5.0F, -2.2386F, -5.848F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-10.0F, 12.0F, 0.0F, -0.3491F, 0.0F, 0.3054F));

		ModelPartData hand = arm_right.addChild("hand", ModelPartBuilder.create().uv(36, 53).cuboid(-3.5089F, -5.7993F, 0.028F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.1392F, 5.1086F, -2.7708F, 2.4504F, 0.1251F, 0.11F));

		ModelPartData palm_right2 = hand.addChild("palm_right2", ModelPartBuilder.create().uv(0, 5).cuboid(-2.4611F, 6.3531F, -0.5058F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.5497F, -14.2941F, 4.7148F, -0.4046F, -0.2137F, 0.0051F));

		ModelPartData finger8 = palm_right2.addChild("finger8", ModelPartBuilder.create().uv(44, 14).cuboid(-1.0915F, 4.7658F, 0.8247F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.1983F, -1.0819F, 1.0716F, -0.3054F, 0.0F, 0.2182F));

		finger8.addChild("bone16", ModelPartBuilder.create().uv(62, 62).cuboid(2.0836F, -0.6069F, 3.5253F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(2.0836F, -1.6069F, 3.5253F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.175F, 1.9024F, -1.659F, -0.48F, 0.0F, 0.0F));

		ModelPartData finger9 = palm_right2.addChild("finger9", ModelPartBuilder.create().uv(20, 19).cuboid(-2.3792F, -0.0883F, 2.991F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.8709F, 2.3153F, -1.5548F, -0.5236F, 0.0F, 0.0F));

		finger9.addChild("bone17", ModelPartBuilder.create().uv(47, 62).cuboid(-0.5315F, -1.6868F, 2.4444F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-0.5315F, -2.6869F, 2.4444F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.8476F, -1.4948F, 0.9673F, -0.48F, 0.0F, 0.0F));

		ModelPartData finger10 = palm_right2.addChild("finger10", ModelPartBuilder.create().uv(0, 19).cuboid(-2.2448F, -2.0029F, 2.114F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.9768F, 3.9014F, -1.5345F, -0.6545F, 0.0F, -0.3403F));

		finger10.addChild("bone18", ModelPartBuilder.create().uv(43, 62).cuboid(-2.2448F, -1.5827F, 1.5329F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-2.2448F, -2.5827F, 1.5329F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0809F, 0.947F, -0.48F, 0.0F, 0.0F));

		ModelPartData pinky3 = palm_right2.addChild("pinky3", ModelPartBuilder.create().uv(28, 62).cuboid(-3.0149F, -1.9028F, 1.4348F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.2094F, 7.4138F, -1.284F, -0.3259F, -0.0647F, 0.6182F));

		ModelPartData bone19 = pinky3.addChild("bone19", ModelPartBuilder.create().uv(55, 63).cuboid(-3.1303F, -1.6905F, 2.2454F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-3.1303F, -2.6905F, 2.2454F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1146F, -3.2141F, -0.4139F, -0.48F, 0.0F, 0.0F));

		ModelPartData thumb3 = palm_right2.addChild("thumb3", ModelPartBuilder.create().uv(48, 14).cuboid(-1.7739F, 1.2146F, -3.2902F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.378F, 7.2968F, -3.5786F, 2.9425F, 0.1215F, 2.4062F));

		ModelPartData bone20 = thumb3.addChild("bone20", ModelPartBuilder.create().uv(33, 47).cuboid(-1.7744F, -2.097F, -4.561F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-1.7744F, -3.097F, -3.561F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.5F, 0.6981F, 0.0F, 0.0F));

		ModelPartData arm_left = body1.addChild("arm_left", ModelPartBuilder.create(), ModelTransform.of(12.2F, 6.5F, -3.4F, -1.5991F, 0.2427F, -3.015F));

		ModelPartData body1_sub_14 = arm_left.addChild("body1_sub_14", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body1_sub_17 = body1_sub_14.addChild("body1_sub_17", ModelPartBuilder.create().uv(0, 46).mirrored().cuboid(-0.0324F, -3.9301F, -3.7389F, 3.0F, 7.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData hand2 = arm_left.addChild("hand2", ModelPartBuilder.create(), ModelTransform.of(0.9571F, 6.2102F, -1.8794F, 2.9417F, 0.0209F, -0.493F));

		ModelPartData body1_sub_16 = hand2.addChild("body1_sub_16", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body1_sub_20 = body1_sub_16.addChild("body1_sub_20", ModelPartBuilder.create().uv(12, 52).mirrored().cuboid(1.3935F, -3.7705F, -0.0439F, 2.0F, 7.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData palm_left = hand2.addChild("palm_left", ModelPartBuilder.create().uv(0, 5).cuboid(-1.0819F, 5.0207F, -3.1902F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.4323F, -6.8957F, -5.7767F, 1.5589F, -0.2137F, 0.0051F));

		ModelPartData finger5 = palm_left.addChild("finger5", ModelPartBuilder.create().uv(44, 14).cuboid(-0.0334F, 4.0477F, -2.2164F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.1983F, -1.0819F, 1.0716F, -0.3054F, 0.0F, 0.2182F));

		ModelPartData bone28 = finger5.addChild("bone28", ModelPartBuilder.create().uv(62, 62).cuboid(3.1416F, 0.1603F, 0.4962F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(3.1416F, -0.8397F, 0.4962F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-3.175F, 1.9024F, -1.659F, -0.48F, 0.0F, 0.0F));

		ModelPartData finger6 = palm_left.addChild("finger6", ModelPartBuilder.create().uv(20, 19).cuboid(-1.0F, 0.1F, 0.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.8709F, 2.3153F, -1.5548F, -0.5236F, 0.0F, 0.0F));

		ModelPartData bone29 = finger6.addChild("bone29", ModelPartBuilder.create().uv(47, 62).cuboid(0.8476F, -0.1388F, -0.1217F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(0.8476F, -1.1388F, -0.1217F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.8476F, -1.4948F, 0.9673F, -0.48F, 0.0F, 0.0F));

		ModelPartData finger7 = palm_left.addChild("finger7", ModelPartBuilder.create().uv(0, 19).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.9768F, 3.9014F, -1.5345F, -0.6545F, 0.0F, -0.3403F));

		finger7.addChild("bone30", ModelPartBuilder.create().uv(43, 62).cuboid(-0.5F, 0.5139F, -0.3227F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-0.5F, -0.4861F, -0.3227F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0809F, 0.947F, -0.48F, 0.0F, 0.0F));

		ModelPartData pinky2 = palm_left.addChild("pinky2", ModelPartBuilder.create().uv(28, 62).cuboid(-0.5F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.6906F, 5.4138F, -2.484F, -0.2617F, 0.2632F, -0.9631F));

		pinky2.addChild("bone31", ModelPartBuilder.create().uv(55, 63).cuboid(-0.6154F, 0.0037F, 0.946F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-0.6154F, -0.9963F, 0.946F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.1146F, -3.2141F, -0.4139F, -0.48F, 0.0F, 0.0F));

		ModelPartData thumb2 = palm_left.addChild("thumb2", ModelPartBuilder.create().uv(48, 14).cuboid(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(3.0221F, 6.1968F, -2.2786F, -2.9381F, 0.1918F, -1.9221F));

		thumb2.addChild("bone32", ModelPartBuilder.create().uv(33, 47).cuboid(-0.5005F, -1.234F, -1.6428F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(53, 60).cuboid(-0.5005F, -2.234F, -0.6428F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.5F, 0.6981F, 0.0F, 0.0F));

		arm_left.addChild("left_jaw_rotation", ModelPartBuilder.create().uv(19, 25).cuboid(-2.5F, -2.5F, -3.5F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.3F, 1.7614F, -1.948F, 0.0F, -1.5708F, 0.0F));

		ModelPartData star = body1.addChild("star", ModelPartBuilder.create().uv(52, 0).cuboid(-4.0F, -17.0F, -3.0F, 7.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-15.0F, 24.0F, -9.0F));

		ModelPartData body2 = modelPartData.addChild("body2", ModelPartBuilder.create().uv(0, 33).cuboid(1.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
		.uv(44, 10).cuboid(-1.0F, 1.5F, 0.5F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(42, 43).cuboid(-1.0F, 4.0F, 0.5F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(43, 31).cuboid(-1.0F, 6.5F, 0.5F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(0, 90).cuboid(1.0F, 0.0F, 0.0F, 3.0F, 10.0F, 3.0F, new Dilation(0.0F))
		.uv(24, 90).cuboid(-3.0F, 1.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 90).cuboid(-3.0F, 4.0F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 90).cuboid(-3.0F, 6.5F, 0.5F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 6.9F, -0.5F));

		ModelPartData rib_right = body2.addChild("rib_right", ModelPartBuilder.create().uv(52, 47).cuboid(-2.1101F, -0.7169F, -3.6211F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(50, 51).cuboid(-2.6965F, 1.7306F, -3.6009F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(44, 58).cuboid(-3.283F, 5.1604F, -3.645F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-4.5445F, 2.9665F, 0.6134F, 0.5385F, -0.7485F, -0.5597F));

		ModelPartData bone5 = rib_right.addChild("bone5", ModelPartBuilder.create().uv(32, 37).cuboid(-2.6909F, -2.5486F, -4.1396F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.389F, -0.9128F, 0.1244F, 0.0F, 0.0F, 0.2618F));

		ModelPartData bone33 = rib_right.addChild("bone33", ModelPartBuilder.create(), ModelTransform.of(-2.878F, 9.6085F, -1.7805F, 0.0F, 0.0F, 0.2618F));

		ModelPartData bone3 = rib_right.addChild("bone3", ModelPartBuilder.create().uv(0, 60).cuboid(-4.8086F, 1.123F, -2.0927F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(62, 7).cuboid(-3.5409F, 5.6123F, 0.1095F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.7097F, -0.5044F, -4.3945F, -0.3054F, -1.9199F, 0.0436F));

		ModelPartData bone6 = bone3.addChild("bone6", ModelPartBuilder.create().uv(57, 59).cuboid(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-2.8F, -2.1012F, -2.2477F, 0.0F, 0.1309F, 0.1745F));

		ModelPartData rib_left = body2.addChild("rib_left", ModelPartBuilder.create(), ModelTransform.of(9.5445F, 2.9665F, 0.6134F, 0.5385F, 0.7485F, 0.5597F));

		ModelPartData body2_sub_5 = rib_left.addChild("body2_sub_5", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body2_sub_2 = body2_sub_5.addChild("body2_sub_2", ModelPartBuilder.create().uv(50, 39).mirrored().cuboid(-3.8899F, -0.7169F, -3.6211F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(50, 35).mirrored().cuboid(-3.3035F, 1.7306F, -3.6009F, 6.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
		.uv(58, 43).mirrored().cuboid(-2.717F, 5.1604F, -3.645F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone9 = rib_left.addChild("bone9", ModelPartBuilder.create(), ModelTransform.of(-0.389F, -0.9128F, 0.1244F, 0.0F, 0.0F, -0.2618F));

		ModelPartData body2_sub_7 = bone9.addChild("body2_sub_7", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body2_sub_10 = body2_sub_7.addChild("body2_sub_10", ModelPartBuilder.create().uv(12, 37).mirrored().cuboid(-5.3091F, -2.5486F, -4.1396F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone10 = rib_left.addChild("bone10", ModelPartBuilder.create(), ModelTransform.of(3.7097F, -0.5044F, -4.3945F, -0.3054F, 1.9199F, -0.0436F));

		ModelPartData body2_sub_9 = bone10.addChild("body2_sub_9", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body2_sub_13 = body2_sub_9.addChild("body2_sub_13", ModelPartBuilder.create().uv(59, 55).mirrored().cuboid(-0.1914F, 1.123F, -2.0927F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 62).mirrored().cuboid(-0.3031F, 3.3935F, -1.0222F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(53, 61).mirrored().cuboid(-0.4591F, 5.6123F, 0.1095F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone11 = bone10.addChild("bone11", ModelPartBuilder.create(), ModelTransform.of(2.8F, -2.1012F, -2.2477F, 0.0F, -0.1309F, -0.1745F));

		ModelPartData body2_sub_11 = bone11.addChild("body2_sub_11", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body2_sub_16 = body2_sub_11.addChild("body2_sub_16", ModelPartBuilder.create().uv(59, 31).mirrored().cuboid(-2.5F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone2 = body2.addChild("bone2", ModelPartBuilder.create(), ModelTransform.of(9.5445F, 2.9665F, 0.6134F, 0.5385F, 0.7485F, 0.5597F));

		ModelPartData bone8 = bone2.addChild("bone8", ModelPartBuilder.create(), ModelTransform.of(0.1719F, -1.0635F, 0.0573F, 0.0F, 0.0F, -0.2618F));

		ModelPartData bone4 = bone2.addChild("bone4", ModelPartBuilder.create(), ModelTransform.of(3.7097F, -0.5044F, -4.3945F, -0.3054F, 1.9199F, -0.0436F));

		ModelPartData bone7 = bone4.addChild("bone7", ModelPartBuilder.create(), ModelTransform.of(-24.3083F, -2.1012F, -2.2477F, 0.0F, 0.1309F, 0.1745F));

		ModelPartData body3 = modelPartData.addChild("body3", ModelPartBuilder.create().uv(50, 14).cuboid(1.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
		.uv(24, 43).cuboid(-1.0F, 1.5F, 0.5F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(12, 90).cuboid(1.0F, 0.0F, 0.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.0F, 16.9F, -0.5F));

		ModelPartData bone3_1 = body3.addChild("bone3_1", ModelPartBuilder.create().uv(12, 41).cuboid(-1.5F, -0.0866F, 0.05F, 3.0F, 8.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(2.5F, 6.1F, 0.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData rib_right2 = bone3_1.addChild("rib_right2", ModelPartBuilder.create().uv(58, 29).cuboid(-2.1101F, -0.7169F, -3.6211F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(58, 25).cuboid(-2.6965F, 2.7306F, -3.6009F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(57, 57).cuboid(-2.2829F, 5.1604F, -4.6449F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-6.3445F, 0.8665F, 0.6134F, 0.3188F, -0.8298F, -0.4033F));

		ModelPartData bone34 = rib_right2.addChild("bone34", ModelPartBuilder.create().uv(20, 59).cuboid(-3.2443F, -2.7108F, -3.9903F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.9179F, -0.3576F, 1.8168F, -0.2451F, -0.2053F, 0.2349F));

		ModelPartData bone37 = bone34.addChild("bone37", ModelPartBuilder.create().uv(59, 14).cuboid(-0.8632F, 3.3273F, -5.2293F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-7.5016F, 0.1909F, -6.4699F, 1.6282F, 0.9648F, -1.3416F));

		ModelPartData bone36 = rib_right2.addChild("bone36", ModelPartBuilder.create().uv(61, 33).cuboid(-2.8918F, 0.3275F, -1.8259F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(19, 61).cuboid(-3.6969F, 3.3935F, -1.0222F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(60, 45).cuboid(-3.5409F, 5.6123F, 0.1095F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-3.7097F, -0.5044F, -4.3945F, -0.3054F, -1.9199F, 0.0436F));

		ModelPartData rib_left2 = bone3_1.addChild("rib_left2", ModelPartBuilder.create(), ModelTransform.of(6.3445F, 0.8665F, 0.6134F, 0.3188F, 0.8298F, 0.4033F));

		ModelPartData body3_sub_6 = rib_left2.addChild("body3_sub_6", ModelPartBuilder.create().uv(57, 27).mirrored().cuboid(-3.8899F, -0.7169F, -3.6211F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(20, 57).mirrored().cuboid(-3.3035F, 2.7306F, -3.6009F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(44, 56).mirrored().cuboid(-2.717F, 5.1604F, -4.6449F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone38 = rib_left2.addChild("bone38", ModelPartBuilder.create(), ModelTransform.of(-2.9179F, -0.3576F, 1.8168F, -0.2451F, 0.2053F, -0.2349F));

		ModelPartData body3_sub_2 = bone38.addChild("body3_sub_2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body3_sub_9 = body3_sub_2.addChild("body3_sub_9", ModelPartBuilder.create().uv(0, 58).mirrored().cuboid(-1.7557F, -2.7108F, -3.9903F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone39 = bone38.addChild("bone39", ModelPartBuilder.create(), ModelTransform.of(7.5016F, 0.1909F, -6.4699F, 1.6282F, -0.9648F, 1.3416F));

		ModelPartData body3_sub_11 = bone39.addChild("body3_sub_11", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body3_sub_12 = body3_sub_11.addChild("body3_sub_12", ModelPartBuilder.create().uv(0, 56).mirrored().cuboid(-4.1368F, 3.3273F, -5.2293F, 5.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData bone41 = rib_left2.addChild("bone41", ModelPartBuilder.create(), ModelTransform.of(3.7097F, -0.5044F, -4.3945F, -0.3054F, 1.9199F, -0.0436F));

		ModelPartData body3_sub_14 = bone41.addChild("body3_sub_14", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData body3_sub_15 = body3_sub_14.addChild("body3_sub_15", ModelPartBuilder.create().uv(44, 60).mirrored().cuboid(-1.1082F, 0.3275F, -1.8259F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(60, 10).mirrored().cuboid(-0.3031F, 3.3935F, -1.0222F, 4.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(9, 33).mirrored().cuboid(0.5409F, 5.6123F, 0.1095F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1 = modelPartData.addChild("head1", ModelPartBuilder.create().uv(0, 5).cuboid(-3.0F, -6.0F, -4.0F, 7.0F, 6.0F, 8.0F, new Dilation(0.0F))
		.uv(14, 5).cuboid(-3.0F, -1.0F, -4.0F, 7.0F, 0.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 68).cuboid(-3.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 1.0F, 0.0F));

		ModelPartData bot_jaw = head1.addChild("bot_jaw", ModelPartBuilder.create().uv(19, 25).cuboid(-2.5F, -3.25F, -3.5F, 5.0F, 5.0F, 7.0F, new Dilation(0.0F))
		.uv(29, 5).cuboid(-2.5F, 0.75F, -3.5F, 5.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, 2.0114F, -0.348F, 0.2182F, 0.0F, 0.0F));

		ModelPartData crown = head1.addChild("crown", ModelPartBuilder.create().uv(44, 55).cuboid(-5.5F, -5.5F, -4.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(50, 24).cuboid(-5.5F, -5.5F, 4.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(0, 23).cuboid(2.5F, -5.5F, -4.5F, 0.0F, 1.0F, 9.0F, new Dilation(0.0F))
		.uv(0, 22).cuboid(-5.5F, -5.5F, -4.5F, 0.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 0.0F, 0.0F));

		ModelPartData crest1 = crown.addChild("crest1", ModelPartBuilder.create().uv(4, 19).cuboid(-2.5F, -4.6897F, -0.0635F, 1.0F, 5.0F, 0.0F, new Dilation(0.0F))
		.uv(46, 5).cuboid(-3.5F, -1.6902F, -0.0634F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(45, 35).cuboid(-1.5F, -1.6902F, -0.0634F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(8, 64).cuboid(-4.5F, -3.6902F, -0.0634F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
		.uv(6, 64).cuboid(-0.5F, -3.6902F, -0.0634F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.5F, -5.8F, -4.6F, 0.5236F, 0.0F, 0.0F));

		ModelPartData crest_a = crest1.addChild("crest_a", ModelPartBuilder.create().uv(42, 43).cuboid(-2.5F, -3.0296F, -2.3999F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest3_a = crest1.addChild("crest3_a", ModelPartBuilder.create().uv(40, 43).cuboid(-4.5F, -5.1641F, -1.9F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest4_a = crest1.addChild("crest4_a", ModelPartBuilder.create().uv(43, 35).cuboid(-4.5F, -5.1641F, -1.9F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest2 = crown.addChild("crest2", ModelPartBuilder.create().uv(4, 64).cuboid(0.0F, -3.8598F, 0.8418F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F))
		.uv(21, 43).cuboid(-1.0F, -0.8598F, 0.8428F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(42, 12).cuboid(1.0F, -0.8598F, 0.8428F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(49, 0).cuboid(-2.0F, -2.8598F, 0.8428F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(37, 47).cuboid(2.0F, -2.8598F, 0.8428F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(3.3F, -5.2F, -0.5F, 0.0F, -1.5708F, 0.5236F));

		ModelPartData crest_a2 = crest2.addChild("crest_a2", ModelPartBuilder.create().uv(40, 31).cuboid(0.0F, -1.7636F, -1.2009F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest3_a2 = crest2.addChild("crest3_a2", ModelPartBuilder.create().uv(40, 12).cuboid(-2.0F, -3.8986F, -0.6991F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest4_a2 = crest2.addChild("crest4_a2", ModelPartBuilder.create().uv(38, 31).cuboid(-2.0F, -3.8981F, -0.7F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest3 = crown.addChild("crest3", ModelPartBuilder.create(), ModelTransform.of(-5.0F, -6.8F, 3.5F, 0.0F, 1.5708F, -0.5236F));

		ModelPartData head1_sub_11 = crest3.addChild("head1_sub_11", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_12 = head1_sub_11.addChild("head1_sub_12", ModelPartBuilder.create().uv(59, 63).mirrored().cuboid(3.0F, -3.1247F, -1.0839F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(38, 12).mirrored().cuboid(4.0F, -0.1247F, -1.0839F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(36, 31).mirrored().cuboid(2.0F, -0.1247F, -1.0839F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 47).mirrored().cuboid(5.0F, -2.1247F, -1.0839F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(9, 46).mirrored().cuboid(1.0F, -2.1247F, -1.0839F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest_a3 = crest3.addChild("crest_a3", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_13 = crest_a3.addChild("head1_sub_13", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_2 = head1_sub_13.addChild("head1_sub_2", ModelPartBuilder.create().uv(36, 12).mirrored().cuboid(3.0F, -0.1646F, -2.5001F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest3_a3 = crest3.addChild("crest3_a3", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_15 = crest3_a3.addChild("head1_sub_15", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_18 = head1_sub_15.addChild("head1_sub_18", ModelPartBuilder.create().uv(17, 35).mirrored().cuboid(5.0F, -2.2981F, -2.001F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest4_a3 = crest3.addChild("crest4_a3", ModelPartBuilder.create(), ModelTransform.of(-4.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_17 = crest4_a3.addChild("head1_sub_17", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_3 = head1_sub_17.addChild("head1_sub_3", ModelPartBuilder.create().uv(9, 35).mirrored().cuboid(5.0F, -2.2981F, -2.001F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest4 = crown.addChild("crest4", ModelPartBuilder.create(), ModelTransform.of(0.5F, -6.8F, 4.0F, -2.618F, 0.0F, 3.1416F));

		ModelPartData head1_sub_19 = crest4.addChild("head1_sub_19", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_24 = head1_sub_19.addChild("head1_sub_24", ModelPartBuilder.create().uv(12, 35).mirrored().cuboid(1.5F, -3.1247F, -1.0839F, 1.0F, 4.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(24, 31).mirrored().cuboid(2.5F, -0.1247F, -1.0839F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(22, 31).mirrored().cuboid(0.5F, -0.1247F, -1.0839F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(0, 46).mirrored().cuboid(3.5F, -2.1247F, -1.0839F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
		.uv(12, 41).mirrored().cuboid(-0.5F, -2.1247F, -1.0839F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest_a4 = crest4.addChild("crest_a4", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_21 = crest_a4.addChild("head1_sub_21", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_27 = head1_sub_21.addChild("head1_sub_27", ModelPartBuilder.create().uv(20, 31).mirrored().cuboid(1.5F, -0.1641F, -2.501F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest3_a4 = crest4.addChild("crest3_a4", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_23 = crest3_a4.addChild("head1_sub_23", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_30 = head1_sub_23.addChild("head1_sub_30", ModelPartBuilder.create().uv(18, 31).mirrored().cuboid(3.5F, -2.2981F, -2.001F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest4_a4 = crest4.addChild("crest4_a4", ModelPartBuilder.create(), ModelTransform.of(-4.0F, 0.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

		ModelPartData head1_sub_25 = crest4_a4.addChild("head1_sub_25", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head1_sub_33 = head1_sub_25.addChild("head1_sub_33", ModelPartBuilder.create().uv(22, 24).mirrored().cuboid(3.5F, -2.2981F, -2.001F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head2 = modelPartData.addChild("head2", ModelPartBuilder.create().uv(24, 13).cuboid(-4.0F, -4.0F, -3.0F, 7.0F, 6.0F, 6.0F, new Dilation(0.0F))
		.uv(32, 68).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, 4.0F, -1.0F));

		ModelPartData crown2 = head2.addChild("crown2", ModelPartBuilder.create().uv(50, 23).cuboid(-5.5F, -5.5F, -4.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(46, 9).cuboid(-5.5F, -5.5F, 2.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(35, 35).cuboid(2.5F, -5.5F, -4.5F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F))
		.uv(35, 34).cuboid(-5.5F, -5.5F, -4.5F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 2.0F, 1.0F));

		ModelPartData crest9 = crown2.addChild("crest9", ModelPartBuilder.create().uv(0, 33).cuboid(-0.5F, -2.1242F, -1.083F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(43, 31).cuboid(-2.5F, -1.1242F, -1.083F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(24, 43).cuboid(1.5F, -1.1242F, -1.083F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.6F, -6.8F, -4.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData crest_a9 = crest9.addChild("crest_a9", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest3_a9 = crest9.addChild("crest3_a9", ModelPartBuilder.create().uv(20, 24).cuboid(-1.5F, -0.1242F, -1.083F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest4_a9 = crest9.addChild("crest4_a9", ModelPartBuilder.create().uv(4, 24).cuboid(-3.5F, -0.1242F, -1.083F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));

		ModelPartData head3 = modelPartData.addChild("head3", ModelPartBuilder.create().uv(0, 19).cuboid(-4.0F, -4.0F, -3.0F, 7.0F, 6.0F, 6.0F, new Dilation(0.0F))
		.uv(32, 68).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, 4.0F, -1.0F));

		ModelPartData crown3 = head3.addChild("crown3", ModelPartBuilder.create().uv(46, 8).cuboid(-5.5F, -5.5F, -4.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(46, 7).cuboid(-5.5F, -5.5F, 2.5F, 8.0F, 1.0F, 0.0F, new Dilation(0.0F))
		.uv(21, 35).cuboid(2.5F, -5.5F, -4.5F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F))
		.uv(21, 34).cuboid(-5.5F, -5.5F, -4.5F, 0.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 2.0F, 1.0F));

		ModelPartData crest8 = crown3.addChild("crest8", ModelPartBuilder.create().uv(0, 0).cuboid(-0.5F, -2.1242F, -1.083F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F))
		.uv(32, 37).cuboid(-2.5F, -1.1242F, -1.083F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F))
		.uv(30, 37).cuboid(1.5F, -1.1242F, -1.083F, 1.0F, 2.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-1.6F, -6.8F, -4.0F, 0.5236F, 0.0F, 0.0F));

		ModelPartData crest_a8 = crest8.addChild("crest_a8", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.5981F, 1.5F, -0.5236F, 0.0F, 0.0F));

		ModelPartData crest3_a8 = crest8.addChild("crest3_a8", ModelPartBuilder.create().uv(2, 24).cuboid(-1.5F, -0.1242F, -1.083F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData crest4_a8 = crest8.addChild("crest4_a8", ModelPartBuilder.create().uv(0, 24).cuboid(-3.5F, -0.1242F, -1.083F, 1.0F, 1.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}

	public void setAngles(SuperNovaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float k = MathHelper.cos(ageInTicks * 0.1F);
		this.ribcage.pitch = (0.065F + 0.05F * k) * 3.1415927F;
		this.tail.setPivot(-2.0F, 6.9F + MathHelper.cos(this.ribcage.pitch) * 10.0F, -0.5F + MathHelper.sin(this.ribcage.pitch) * 10.0F);
		this.tail.pitch = (0.265F + 0.1F * k) * 3.1415927F;
		this.centerHead.yaw = netHeadYaw * 0.017453292F;
		this.centerHead.pitch = headPitch * 0.017453292F;
	}

	public void animateModel(T witherEntity, float f, float g, float h) {
		rotateHead(witherEntity, this.rightHead, 0);
		rotateHead(witherEntity, this.leftHead, 1);
	}
	private static <T extends SuperNovaEntity> void rotateHead(T entity, ModelPart head, int sigma) {
		head.yaw = (entity.getHeadYaw(sigma) - entity.bodyYaw) * 0.017453292F;
		head.pitch = entity.getHeadPitch(sigma) * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		ribcage.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		centerHead.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		rightHead.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		leftHead.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}