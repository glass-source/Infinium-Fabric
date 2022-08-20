package com.infinium.server.items.custom.tools.utility;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GrapplingHookItem extends FishingRodItem {

    public GrapplingHookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        var itemStack = user.getStackInHand(hand);
        var vec = user.getRotationVector().multiply(1.25f);

        if (!cooldownManager.isCoolingDown(this)) {
            if (!world.isClient) itemStack.damage(1, user, p -> p.sendToolBreakStatus(hand));

            user.setVelocity(vec.getX() * 2.25, vec.getY() * 3.25, vec.getZ() * 2.25);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f);
            cooldownManager.set(this, 20);
            super.use(world, user, hand);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }


}
