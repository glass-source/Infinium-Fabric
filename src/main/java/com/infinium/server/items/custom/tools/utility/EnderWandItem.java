package com.infinium.server.items.custom.tools.utility;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderWandItem extends Item implements InfiniumItem {
    public EnderWandItem(Settings settings) {
        super(settings);
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            var loc = user.getRotationVector().multiply(12);
            var locToTP = user.getEyePos().add(loc);
            var blockPos = new BlockPos(locToTP.getX(), locToTP.getY(), locToTP.getZ());

            if (world.getBlockState(blockPos).getBlock().asItem().equals(Items.AIR)) {
                user.teleport(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                user.getStackInHand(hand).damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
            } else {
                for (int i = 12; i > 0; i--) {
                    loc = user.getRotationVector().multiply(i);
                    locToTP = user.getEyePos().add(loc);
                    if (world.getBlockState(blockPos).getBlock().asItem().equals(Items.AIR)) {
                        user.teleport(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                        user.getStackInHand(hand).damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                        break;
                    }
                }
            }
        }
        user.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.05F);
        user.getItemCooldownManager().set(this, 15);
        return super.use(world, user, hand);
    }

}
