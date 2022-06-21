package com.infinium.global.mixin.screen;

import com.infinium.Infinium;
import com.infinium.api.events.players.PlayerPauseEvent;
import com.infinium.client.InfiniumClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin {

    @Inject(method = "initWidgets", at = @At("HEAD"))
    public void checkPauseMenu(CallbackInfo ci){
        PlayerPauseEvent.EVENT.invoker().pause(InfiniumClient.getPlayer());
    }

}
