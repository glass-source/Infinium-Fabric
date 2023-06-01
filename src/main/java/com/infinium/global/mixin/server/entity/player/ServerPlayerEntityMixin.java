package com.infinium.global.mixin.server.entity.player;

import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {

    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }
    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float applyMadness(float amount) {

        if (this.hasStatusEffect(InfiniumEffects.MADNESS)) {
            var level = this.getStatusEffect(InfiniumEffects.MADNESS).getAmplifier() + 1;
            return amount * (level + (level * 0.25f));
        } else {
            return amount;
        }
    }
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void onPlayerDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.hasStatusEffect(InfiniumEffects.IMMUNITY)) {
            cir.setReturnValue(false);
            cir.cancel();
        }
        PlayerDamageEvent.EVENT.invoker().onPlayerDamage(this.getUuid(), source, amount, cir.isCancelled());
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
