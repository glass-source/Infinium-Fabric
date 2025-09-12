package com.infinium.global.mixin.server.entity;

import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityDataSaver {

    @Unique
    private NbtCompound persistentData;

    @Override
    public NbtCompound infinium_Fabric$getPersistentData(){
        if (this.persistentData == null) this.persistentData = new NbtCompound();
        return this.persistentData;
    }

    @Inject(method = "writeNbt", at = @At("HEAD"))
    private void saveData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put("infinium.data", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void saveData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("infinium.data", 10)) {
            persistentData = nbt.getCompound("infinium.data");
        }
    }



}
