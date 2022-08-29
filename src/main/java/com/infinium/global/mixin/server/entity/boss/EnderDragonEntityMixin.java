package com.infinium.global.mixin.server.entity.boss;

import com.infinium.Infinium;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.stat.Stats;
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

    @Inject(method = "kill", at = @At("TAIL"))
    public void injectAtTail(CallbackInfo ci) {
        Infinium.getInstance().getCore().getSanityManager().totalPlayers.forEach(p -> p.updateKilledAdvancementCriterion(this, 1, this.getRecentDamageSource()));
    }
}
