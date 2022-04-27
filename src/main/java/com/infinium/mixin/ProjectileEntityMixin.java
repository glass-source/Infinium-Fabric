package com.infinium.mixin;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    @SuppressWarnings("cast")
    @Inject(method = "createSpawnPacket", at = @At("HEAD"))
    public void sendTridentStackOnSpawn(CallbackInfoReturnable<Packet<?>> info) {
        PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());

    }




}
