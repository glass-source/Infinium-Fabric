package com.infinium.server.items.custom.tools.utility;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderWandItem extends ToolItem implements InfiniumItem {


    public EnderWandItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!cooldownManager.isCoolingDown(this)) {
            if (!world.isClient()) {
                var loc = user.getRotationVector().multiply(7);
                var locToTP = user.getEyePos().add(loc);
                var blockPos = new BlockPos(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                if (world.getBlockState(blockPos).getBlock().asItem().equals(Items.AIR)) {
                    user.teleport(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                    user.getStackInHand(hand).damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                } else {
                    for (int i = 7; i > 0; i--) {
                        loc = user.getRotationVector().multiply(i);
                        locToTP = user.getEyePos().add(loc);
                        if (world.getBlockState(blockPos).getBlock().asItem().equals(Items.AIR)) {
                            user.teleport(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                            user.getStackInHand(hand).damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                            i = 0;
                        }
                    }
                }
            }
            user.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 10, 0.3F);
            user.getItemCooldownManager().set(this, 20);
        }
        return super.use(world, user, hand);
    }


}
