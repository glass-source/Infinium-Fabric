package com.infinium.server.items.custom.tools.utility;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class GrapplingHookItem extends FishingRodItem implements InfiniumItem {

    public GrapplingHookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        var itemStack = user.getStackInHand(hand);
        var vec = user.getRotationVector().multiply(1.25f).add(user.getVelocity().multiply(new Random().nextDouble(0.24, 1.6)));

        if (!cooldownManager.isCoolingDown(this)) {
            if (!world.isClient) itemStack.damage(1, user, p -> p.sendToolBreakStatus(hand));
            user.setVelocity(vec.getX() * 1.75, vec.getY() * 2.55, vec.getZ() * 1.75);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0f, 0.4f);
            cooldownManager.set(this, 45);
            super.use(world, user, hand);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.success(user.getStackInHand(hand));
    }


}
