package com.infinium.mixin.render;

import com.infinium.Infinium;
import com.infinium.entity.MagmaTridentEntity;
import com.infinium.items.ModItems;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.infinium.Infinium.id;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At(value = "HEAD"), cancellable = true)
    public void getTextureMixin(TridentEntity entity, CallbackInfoReturnable<Identifier> cir) {
        if(entity instanceof MagmaTridentEntity || MagmaTridentEntity.isMagmaTrident(entity)) {
            cir.setReturnValue(id("textures/entity/magma_trident.png"));
            Infinium.LOGGER.debug("hola");
        }
    }

}
