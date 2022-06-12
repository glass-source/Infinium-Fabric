package com.infinium.api.entities.render.voidghast;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

public class VoidGhastEntityModel<T extends Entity> extends SinglePartEntityModel<T> {

    private final ModelPart root;
    private final ModelPart[] tentacles = new ModelPart[9];

    public VoidGhastEntityModel(ModelPart root) {
        this.root = root;

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
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 17.6F, 0.0F));
        Random random = new Random(1660L);

        for(int i = 0; i < 9; ++i) {
            float f = (((float)(i % 3) - (float)(i / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
            float g = ((float)(i / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
            int j = random.nextInt(7) + 8;
            modelPartData.addChild(getTentacleName(i), ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, (float)j, 2.0F), ModelTransform.pivot(f, 24.6F, g));
        }
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        for(int i = 0; i < this.tentacles.length; ++i) {
            this.tentacles[i].pitch = 0.2F * MathHelper.sin(animationProgress * 0.3F + (float)i) + 0.4F;
        }

    }

    public ModelPart getPart() {
        return this.root;
    }

}
