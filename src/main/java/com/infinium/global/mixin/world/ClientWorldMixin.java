package com.infinium.global.mixin.world;

import com.infinium.api.events.entity.EntitySpawn;
import com.infinium.api.world.Location;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin {


   @Inject(method = "addEntity", at = @At("HEAD"), cancellable = true)
   public void onSpawnEntity(int id, Entity entity, CallbackInfo ci) {


      Location loc = new Location(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ());
      EntitySpawn.EVENT.invoker().spawn(entity, loc);
   }


}
