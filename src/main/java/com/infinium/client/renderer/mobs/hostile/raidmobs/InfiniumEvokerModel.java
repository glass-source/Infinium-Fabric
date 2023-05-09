package com.infinium.client.renderer.mobs.hostile.raidmobs;

import com.infinium.Infinium;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class InfiniumEvokerModel<T extends IllagerEntity> extends IllagerEntityModel<T> {

    public static final EntityModelLayer INFINIUM_EVOKER = new EntityModelLayer(new Identifier(Infinium.MOD_ID, "infinium_evoker"), "infinium_evoker");
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart hat;
    private final ModelPart arms;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public InfiniumEvokerModel(ModelPart root) {
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
        ModelPartData head1 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData head2 = head1.addChild("head2", ModelPartBuilder.create().uv(101, 54).cuboid(-3.0F, -1.0F, -4.0F, 7.0F, 3.0F, 1.0F, new Dilation(0.0F)).uv(102, 53).cuboid(-2.5F, -1.0F, -3.0F, 6.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(105, 53).cuboid(-3.75F, -1.5F, -4.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(105, 53).cuboid(2.75F, -1.5F, -4.25F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.5F, 1.0F, -1.25F, -0.1309F, 0.0F, 0.0F));
        ModelPartData head3 = head2.addChild("head3", ModelPartBuilder.create().uv(105, 51).cuboid(-1.0F, 1.65F, -5.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(105, 51).cuboid(-2.15F, 0.0F, -5.65F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-1.5F, 0.25F, 1.0F, -0.1933F, 0.1018F, 0.4788F));
        ModelPartData head4 = head3.addChild("head4", ModelPartBuilder.create().uv(107, 56).cuboid(4.75F, -1.0F, -6.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)).uv(102, 52).cuboid(5.65F, -2.75F, -6.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(2.15F, 2.75F, 1.25F, 0.041F, 0.0149F, -0.7851F));
        head4.addChild("head5", ModelPartBuilder.create().uv(103, 54).cuboid(1.0F, 0.25F, -3.75F, 4.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(-0.65F, -3.0F, -2.25F, 0.0F, 0.0F, 0.3054F));
        ModelPartData hat = head1.addChild("hat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 12.0F, 8.0F, new Dilation(0.45F)), ModelTransform.NONE);
        hat.addChild("hat2", ModelPartBuilder.create().uv(41, 64).cuboid(-5.0F, -39.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -0.25F));
        head1.addChild("nose", ModelPartBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -6.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(0.0F, -2.0F, 0.0F));
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(16, 20).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 6.0F).uv(0, 38).cuboid(-4.0F, 0.0F, -3.0F, 8.0F, 18.0F, 6.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("cape", ModelPartBuilder.create().uv(8, 66).cuboid(-5.0F, -10.5F, 0.0F, 10.0F, 21.0F, 0.0F, new Dilation(0.5F)), ModelTransform.of(0.0F, 10.5F, 4.0F, 0.0873F, 0.0F, 0.0F));
        ModelPartData modelPartData3 = modelPartData.addChild("arms", ModelPartBuilder.create().uv(44, 22).cuboid(-8.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F).uv(40, 38).cuboid(-4.0F, 2.0F, -2.0F, 8.0F, 4.0F, 4.0F), ModelTransform.of(0.0F, 3.0F, -1.0F, -0.75F, 0.0F, 0.0F));
        modelPartData3.addChild("left_shoulder", ModelPartBuilder.create().uv(44, 22).mirrored().cuboid(4.0F, -2.0F, -2.0F, 4.0F, 8.0F, 4.0F), ModelTransform.NONE);
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(0, 22).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(0, 22).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(40, 46).cuboid(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(-5.0F, 2.0F, 0.0F));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(40, 46).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F), ModelTransform.pivot(5.0F, 2.0F, 0.0F));
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

        IllagerEntity.State state = illagerEntity.getState();

        switch (state) {
            case SPELLCASTING -> {
                this.rightArm.pivotZ = 0.0F;
                this.rightArm.pivotX = -5.0F;
                this.leftArm.pivotZ = 0.0F;
                this.leftArm.pivotX = 5.0F;
                this.rightArm.pitch = MathHelper.cos(h * 0.6662F) * 0.25F;
                this.leftArm.pitch = MathHelper.cos(h * 0.6662F) * 0.25F;
                this.rightArm.roll = 2.3561945F;
                this.leftArm.roll = -2.3561945F;
                this.rightArm.yaw = 0.0F;
                this.leftArm.yaw = 0.0F;
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