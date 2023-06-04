package com.infinium.global.mixin.server.entity.boss;

import com.infinium.Infinium;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public class EnderDragonEntityMixin extends MobEntity implements Monster {

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    public void initGoals(CallbackInfo ci) {
        if (Infinium.getInstance().getDateUtils() == null) return;
        if (Infinium.getInstance().getDateUtils().getCurrentDay() >= 4) {

        }
    }

    @Inject(method = "kill", at = @At("TAIL"))
    public void kill(CallbackInfo ci) {
        Infinium.getInstance().getCore().getTotalPlayers().forEach(p -> Criteria.PLAYER_KILLED_ENTITY.trigger(p, this, this.getRecentDamageSource()));
    }

}
