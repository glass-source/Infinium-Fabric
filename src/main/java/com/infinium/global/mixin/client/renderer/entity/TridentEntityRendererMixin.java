package com.infinium.global.mixin.client.renderer.entity;

import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.infinium.Infinium.id;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At(value = "HEAD"), cancellable = true)
    public void getTextureMixin(TridentEntity entity, CallbackInfoReturnable<Identifier> cir) {
        if (entity.getName().asString().equals("Magma Trident")) cir.setReturnValue(id("textures/entity/magma_trident.png"));
    }


}
