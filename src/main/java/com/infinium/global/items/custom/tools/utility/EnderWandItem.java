package com.infinium.global.items.custom.tools.utility;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnderWandItem extends ToolItem {


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
                } else {
                    for (int i = 7; i > 0; i--) {
                        loc = user.getRotationVector().multiply(i);
                        locToTP = user.getEyePos().add(loc);
                        if (world.getBlockState(blockPos).getBlock().asItem().equals(Items.AIR)) {
                            user.teleport(locToTP.getX(), locToTP.getY(), locToTP.getZ());
                            i = 0;
                        }
                    }
                }
            }

            if (user.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.MAINHAND), world, user);
            } else if (user.getEquippedStack(EquipmentSlot.OFFHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.OFFHAND), world, user);
            }

            user.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 10, 0.3F);
            user.getItemCooldownManager().set(this, 20);
        }
        return super.use(world, user, hand);
    }


}
