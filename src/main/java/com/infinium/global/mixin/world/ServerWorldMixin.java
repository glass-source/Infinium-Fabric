package com.infinium.global.mixin.world;

import com.infinium.api.events.entity.EntitySpawn;
import com.infinium.api.world.Location;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

    @Inject(method = "addEntity", at = @At("HEAD"))
    public void onSpawnEntity(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        Location loc = new Location(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
        EntitySpawn.EVENT.invoker().spawn(entity, loc);
    }
}
