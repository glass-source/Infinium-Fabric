package com.infinium.global.mixin;

import com.infinium.global.utils.EntityDataSaver;
import net.minecraft.entity.Entity;
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
    private void saveData(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        if (persistentData != null) {
            nbt.put("infinium.sanity", persistentData);
            nbt.put("infinium.totems", persistentData);
            nbt.put("infinium.cooldown", persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    private void saveData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("infinium.data", 10)) {
            persistentData = nbt.getCompound("infinium.data");
        }
    }

}
