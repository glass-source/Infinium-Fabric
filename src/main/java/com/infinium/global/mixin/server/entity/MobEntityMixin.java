package com.infinium.global.mixin.server.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    @Shadow public abstract void setAttacking(boolean attacking);
    private MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);

    }

    @Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    private void onSelectTarget(LivingEntity target, CallbackInfo ci) {
        if (target == null) return;
        if (target.isDead()) return;
        if (!target.isPlayer()) return;



    }

}
