package com.infinium.global.mixin.client.entity;

import com.infinium.networking.packets.flashbang.FlashbangManager;
import com.infinium.networking.packets.sanity.KairosCheckC2SPacket;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectOnTick(CallbackInfo ci) {
        FlashbangManager.tick();
    }

}
