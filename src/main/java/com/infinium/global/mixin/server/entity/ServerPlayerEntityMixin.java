package com.infinium.global.mixin.server.entity;

import com.infinium.Infinium;
import com.infinium.server.events.players.PlayerDamageEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }
    @Inject(method = "damage", at = @At("TAIL"))
    private void onPlayerDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (cir.isCancelled() || source == null || amount < 0) return;
        PlayerDamageEvent.EVENT.invoker().onPlayerDamage(this.getUuid(), source);
    }
    @Inject(method = "updatePotionVisibility", at = @At("TAIL"), cancellable = true)
    private void removeInvisPotion(CallbackInfo ci) {
        if (!this.hasStatusEffect(StatusEffects.INVISIBILITY)) return;
        if (Infinium.getInstance().getDateUtils() == null) return;

        var day = Infinium.getInstance().getDateUtils().getCurrentDay();

        if (day >= 14) {
            ci.cancel();
            this.setInvisible(false);
        }
    }



}
