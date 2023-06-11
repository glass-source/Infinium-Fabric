package com.infinium.global.mixin.server.entity.boss;

import com.infinium.Infinium;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntity implements Monster {
    @Shadow @Nullable public abstract EnderDragonFight getFight();

    @Shadow @Final private @Nullable EnderDragonFight fight;

    protected EnderDragonEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "kill", at = @At("TAIL"))
    private void kill(CallbackInfo ci) {
        try {
            Infinium.getInstance().getCore().getTotalPlayers().forEach(player -> Criteria.PLAYER_KILLED_ENTITY.trigger(player, this, DamageSource.GENERIC));


        } catch (Exception ex) {
            ex.printStackTrace();;
        }
    }


}
