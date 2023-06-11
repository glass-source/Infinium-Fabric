package com.infinium.global.mixin.server.entity.boss;

import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(EnderDragonFight.class)
public abstract class EnderDragonFightMixin  {
    @Shadow @Final private ServerBossBar bossBar;
    @Shadow private @Nullable UUID dragonUuid;

    @Inject(method = "updateFight", at = @At("TAIL"))
    private void updateFight(EnderDragonEntity dragon, CallbackInfo ci) {
        if (dragon.getUuid().equals(this.dragonUuid)) {
            this.bossBar.setColor(BossBar.Color.BLUE);
            this.bossBar.setStyle(BossBar.Style.NOTCHED_12);
        }
    }

}
