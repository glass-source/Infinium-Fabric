package com.infinium.global.mixin.server.item;

import com.infinium.Infinium;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public class FireworkRocketItemMixin extends Item {

    public FireworkRocketItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void onUse(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (Infinium.getInstance().getCore().getDateUtils().getCurrentDay() >= 14) {
            if (user.isFallFlying()) {
                ItemStack itemStack = user.getStackInHand(hand);
                if (!world.isClient) {
                    FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user);
                    world.spawnEntity(fireworkRocketEntity);
                    if (!user.getAbilities().creativeMode) {
                        itemStack.decrement(3);
                    }
                    user.incrementStat(Stats.USED.getOrCreateStat(this));
                }
                cir.setReturnValue(TypedActionResult.success(user.getStackInHand(hand), world.isClient()));
            }
            cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
        }
    }

}
