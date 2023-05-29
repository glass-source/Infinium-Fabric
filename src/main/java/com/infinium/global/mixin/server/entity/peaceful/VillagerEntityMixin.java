package com.infinium.global.mixin.server.entity.peaceful;

import com.infinium.Infinium;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity {
    protected VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);

    }

    @Inject(method = "createVillagerAttributes", at = @At("RETURN"))
    private static void createAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir){
        cir.getReturnValue().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 1.0D);
    }

    @Inject(method = "initBrain", at = @At("HEAD"))
    private void addGoals(CallbackInfo ci){
        if (Infinium.getInstance().getDateUtils() == null) return;
        if (Infinium.getInstance().getDateUtils().getCurrentDay() >= 7) {
            if (this.targetSelector != null && this.goalSelector != null) {
                targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
                goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
            }
        }
    }

}
