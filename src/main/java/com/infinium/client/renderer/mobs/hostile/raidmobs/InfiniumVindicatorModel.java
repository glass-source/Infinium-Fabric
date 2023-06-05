package com.infinium.client.renderer.mobs.hostile.raidmobs;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class InfiniumVindicatorModel<T extends IllagerEntity> extends IllagerEntityModel<T> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public InfiniumVindicatorModel(ModelPart root) {
        super(root);
        this.root = root;
        this.head = root.getChild("head");
        this.hat = this.head.getChild("hat");
        this.hat.visible = true;
        this.arms = root.getChild("arms");
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        this.leftArm = root.getChild("left_arm");
        this.rightArm = root.getChild("right_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("berserker_head", ModelPartBuilder.create().uv(97, 111).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("hat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        head.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
        head.addChild("hat2", ModelPartBuilder.create().uv(0, 95).cuboid(-5.0F, -34.0F, -5.0F, 10.0F, 8.0F, 9.0F, new Dilation(0.0F)).uv(27, 97).cuboid(-2.0F, -37.0F, -5.0F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(40, 108).cuboid(-8.0F, -33.0F, -2.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(40, 108).cuboid(5.0F, -33.0F, -2.0F, 3.0F, 2.0F, 2.0F, new Dilation(0.0F)).uv(42, 104).cuboid(-8.0F, -36.0F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(42, 104).cuboid(6.0F, -36.0F, -2.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.0F)).uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData parrot = body.addChild("parrot", ModelPartBuilder.create(), ModelTransform.pivot(-6.0F, 1.0F, 0.0F));
        ModelPartData head2 = parrot.addChild("head2", ModelPartBuilder.create().uv(0, 66).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)).uv(8, 66).cuboid(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(8, 67).cuboid(-0.5F, -1.0F, -2.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(16, 67).cuboid(-0.5F, -1.25F, -2.95F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, -0.5F));
        head2.addChild("feather", ModelPartBuilder.create().uv(4, 71).cuboid(0.0F, -4.5F, 0.0F, 0.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.5F, -2.0F, -0.2618F, 0.0F, 0.0F));
        parrot.addChild("body2", ModelPartBuilder.create().uv(12, 71).cuboid(-1.5F, 0.5F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.5F, -1.0F));

        ModelPartData left_wing = parrot.addChild("left_wing", ModelPartBuilder.create(), ModelTransform.pivot(-1.5F, -7.1F, -0.8F));

        left_wing.addChild("left_wing_rotation", ModelPartBuilder.create().uv(24, 71).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, 2.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        ModelPartData right_wing = parrot.addChild("right_wing", ModelPartBuilder.create(), ModelTransform.pivot(1.5F, -7.1F, -0.8F));

        right_wing.addChild("right_wing_rotation", ModelPartBuilder.create().uv(24, 71).cuboid(-0.5F, -2.0F, -1.5F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-3.0F, 2.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        parrot.addChild("left_leg2", ModelPartBuilder.create().uv(28, 66).cuboid(-0.5F, 0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, -2.0F, -1.0F, -0.6981F, 0.0F, 0.0F));

        parrot.addChild("right_leg2", ModelPartBuilder.create().uv(28, 66).cuboid(-0.5F, 0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, -2.0F, -1.0F, -0.6981F, 0.0F, 0.0F));

        parrot.addChild("tail", ModelPartBuilder.create().uv(20, 66).cuboid(-1.5F, -0.5F, -1.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.9F, 1.2F, 0.5236F, 0.0F, 0.0F));

        ModelPartData armor = body.addChild("armor", ModelPartBuilder.create().uv(61, 20).cuboid(-4.0F, 1.0F, -3.0F, 8.0F, 12.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData chest = armor.addChild("chest", ModelPartBuilder.create().uv(61, 43).cuboid(1.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F, new Dilation(0.5F)), ModelTransform.of(0.0F, 4.0F, -2.5F, -0.2618F, 0.0F, 0.0F));

        chest.addChild("body_sub_2", ModelPartBuilder.create().uv(61, 43).mirrored().cuboid(-5.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F, new Dilation(0.5F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData arms = modelPartData.addChild("arms", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 3.5F, 0.3F));

        ModelPartData arms_rotation = arms.addChild("arms_rotation", ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)).uv(40, 38).cuboid(-4.0F, 4.0F, -2.0F, 8.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 0.05F, -0.7505F, 0.0F, 0.0F));

        arms_rotation.addChild("arms_flipped", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -24.0F, -2.0F, 4.0F, 8.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData berserker = arms.addChild("berserker", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm2 = berserker.addChild("left_arm2", ModelPartBuilder.create().uv(0, 112).mirrored().cuboid(9.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.0F, -1.5F, -0.3F));

        left_arm2.addChild("left_shoulder2", ModelPartBuilder.create().uv(32, 116).cuboid(-9.0F, -24.0F, -2.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)).uv(54, 117).cuboid(-9.0F, -15.0F, -2.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(18.0F, 21.0F, -1.0F));

        ModelPartData right_arm2 = berserker.addChild("right_arm2", ModelPartBuilder.create().uv(0, 112).cuboid(-13.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -1.5F, -0.3F));

        ModelPartData right_shoulder2 = right_arm2.addChild("right_shoulder2", ModelPartBuilder.create(), ModelTransform.pivot(-18.0F, 21.0F, -1.0F));

        right_shoulder2.addChild("right_arm_sub_2", ModelPartBuilder.create().uv(32, 116).mirrored().cuboid(4.0F, -24.0F, -2.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false).uv(54, 117).mirrored().cuboid(4.0F, -15.0F, -2.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_arm = modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(49, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, 2.0F, 0.0F));

        left_arm.addChild("left_shoulder", ModelPartBuilder.create().uv(69, 64).cuboid(-9.0F, -24.0F, -2.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)).uv(91, 65).cuboid(-9.0F, -15.0F, -2.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(8.0F, 21.0F, -1.0F));

        ModelPartData right_arm = modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(49, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));

        ModelPartData right_shoulder = right_arm.addChild("right_shoulder", ModelPartBuilder.create(), ModelTransform.pivot(-8.0F, 21.0F, -1.0F));

        right_shoulder.addChild("right_arm_sub_1", ModelPartBuilder.create().uv(69, 64).mirrored().cuboid(4.0F, -24.0F, -2.0F, 5.0F, 4.0F, 6.0F, new Dilation(0.0F)).mirrored(false).uv(91, 65).mirrored().cuboid(4.0F, -15.0F, -2.0F, 5.0F, 5.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData left_leg = modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 12.0F, 0.0F));

        ModelPartData left_boot = left_leg.addChild("left_boot", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, 6.0F, 0.0F));
        left_boot.addChild("left_leg_sub_1", ModelPartBuilder.create().uv(110, 34).mirrored().cuboid(2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.25F)).mirrored(false).uv(118, 30).mirrored().cuboid(2.0F, -3.0F, -3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false).uv(118, 30).mirrored().cuboid(2.0F, -3.0F, 2.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_leg = modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(30, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        right_leg.addChild("right_boot", ModelPartBuilder.create().uv(110, 34).cuboid(-6.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.25F)).uv(118, 30).cuboid(-6.0F, -3.0F, -3.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(118, 30).cuboid(-6.0F, -3.0F, 2.0F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 6.0F, 0.0F));
        right_leg.addChild("wood_leg", ModelPartBuilder.create().uv(60, 0).cuboid(-5.5F, -2.0F, -6.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(52, 0).cuboid(-6.0F, -3.0F, -7.0F, 2.0F, 1.0F, 2.0F, new Dilation(0.0F)).uv(40, 0).cuboid(-6.5F, -4.0F, -7.5F, 3.0F, 1.0F, 3.0F, new Dilation(0.0F)).uv(48, 3).cuboid(-7.0F, -5.0F, -8.0F, 4.0F, 1.0F, 4.0F, new Dilation(0.0F)).uv(44, 8).cuboid(-7.5F, -7.0F, -8.5F, 5.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, 12.0F, 6.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    public ModelPart getPart() {
        return this.root;
    }

    public void setAngles(T illagerEntity, float f, float g, float h, float i, float j) {
        this.head.yaw = i * 0.017453292F;
        this.head.pitch = j * 0.017453292F;

        if (this.riding) {
            this.rightArm.pitch = -0.62831855F;
            this.rightArm.yaw = 0.0F;
            this.rightArm.roll = 0.0F;
            this.leftArm.pitch = -0.62831855F;
            this.leftArm.yaw = 0.0F;
            this.leftArm.roll = 0.0F;
            this.rightLeg.pitch = -1.4137167F;
            this.rightLeg.yaw = 0.31415927F;
            this.rightLeg.roll = 0.07853982F;
            this.leftLeg.pitch = -1.4137167F;
            this.leftLeg.yaw = -0.31415927F;
            this.leftLeg.roll = -0.07853982F;
        } else {
            this.rightArm.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 2.0F * g * 0.5F;
            this.rightArm.yaw = 0.0F;
            this.rightArm.roll = 0.0F;
            this.leftArm.pitch = MathHelper.cos(f * 0.6662F) * 2.0F * g * 0.5F;
            this.leftArm.yaw = 0.0F;
            this.leftArm.roll = 0.0F;
            this.rightLeg.pitch = MathHelper.cos(f * 0.6662F) * 1.4F * g * 0.5F;
            this.rightLeg.yaw = 0.0F;
            this.rightLeg.roll = 0.0F;
            this.leftLeg.pitch = MathHelper.cos(f * 0.6662F + 3.1415927F) * 1.4F * g * 0.5F;
            this.leftLeg.yaw = 0.0F;
            this.leftLeg.roll = 0.0F;
        }

        try {
            IllagerEntity.State state = illagerEntity.getState();

            switch (state) {
                case ATTACKING -> {
                    if (illagerEntity.getMainHandStack().isEmpty()) {
                        CrossbowPosing.meleeAttack(this.leftArm, this.rightArm, true, this.handSwingProgress, h);
                    } else {
                        CrossbowPosing.meleeAttack(this.rightArm, this.leftArm, illagerEntity, this.handSwingProgress, h);
                    }
                }

                case CELEBRATING -> {
                    this.rightArm.pivotZ = 0.0F;
                    this.rightArm.pivotX = -5.0F;
                    this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.05F;
                    this.rightArm.roll = 2.670354F;
                    this.rightArm.yaw = 0.0F;
                    this.leftArm.pivotZ = 0.0F;
                    this.leftArm.pivotX = 5.0F;
                    this.leftArm.pitch = MathHelper.cos(h * 0.6662F) * 0.05F;
                    this.leftArm.roll = -2.3561945F;
                    this.leftArm.yaw = 0.0F;
                }
            }

            boolean bl = state == IllagerEntity.State.CROSSED;
            this.arms.visible = bl;
            this.leftArm.visible = !bl;
            this.rightArm.visible = !bl;
        } catch (Exception ignored) {}
    }


    private ModelPart getAttackingArm(Arm arm) {
        return arm == Arm.LEFT ? this.leftArm : this.rightArm;
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
