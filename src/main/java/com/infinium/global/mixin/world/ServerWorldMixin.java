package com.infinium.global.mixin.world;

import com.infinium.api.events.entity.EntitySpawnEvent;
import com.infinium.api.world.Location;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Shadow @Final List<ServerPlayerEntity> players;
    @Shadow @Final private ServerWorldProperties worldProperties;

    // Sends the WorldTimeUpdateS2CPacket to all the online players immediately after changing the world time to fix some timing bugs.
    @Inject(at = @At("TAIL"), method = "setTimeOfDay")
    public void fixTimePacketBug(long timeOfDay, CallbackInfo ci) {
        players.forEach(p -> p.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(worldProperties.getTime(), worldProperties.getTimeOfDay(), worldProperties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))));
    }

    @Inject(method = "addEntity", at = @At("TAIL"))
    public void onSpawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        EntitySpawnEvent.EVENT.invoker().spawn(entity);
    }
}
