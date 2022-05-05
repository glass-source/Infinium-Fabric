package com.infinium.global.mixin;

//Infinium Player: la secuela

import com.infinium.api.utils.EntityDataSaver;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class PlayerDataSaver implements EntityDataSaver {

    private NbtCompound persistentData;

    @Override
    public NbtCompound getPersistentData(){
        if (this.persistentData == null) this.persistentData = new NbtCompound();
        return this.persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void saveSanity(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put("infinium.cordura", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void saveSanity(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("infinium.cordura", 10)) {
            persistentData = nbt.getCompound("infinium.cordura");
        }
    }

}
