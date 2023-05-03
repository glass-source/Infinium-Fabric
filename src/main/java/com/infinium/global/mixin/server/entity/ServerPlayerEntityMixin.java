package com.infinium.global.mixin.server.entity;

import com.infinium.Infinium;
import com.infinium.global.utils.DateUtils;
import com.infinium.server.events.players.PlayerDamageEvent;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
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
public class ServerPlayerEntityMixin extends PlayerEntity {

    @Inject(method = "damage", at = @At("TAIL"), cancellable = true)
    private void onPlayerDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        PlayerDamageEvent.EVENT.invoker().onPlayerDamage(this.getUuid());
    }
    @Inject(method = "updatePotionVisibility", at = @At("TAIL"), cancellable = true)
    private void removePotionVisibility(CallbackInfo ci) {
        if (this.isSpectator()) return;
        if (Infinium.getInstance().getDateUtils() == null) return;
        var day = Infinium.getInstance().getDateUtils().getCurrentDay();

        if (day >= 14) {
            ci.cancel();
            this.setInvisible(false);
        }
    }
    public ServerPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }
}
