package com.infinium.server.entities;

import com.infinium.global.utils.ChatFormatter;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.DyeColor;
import net.minecraft.world.ServerWorldAccess;

public interface InfiniumEntity {

    default void setTransBanner(ServerWorldAccess world, MobEntity entity) {
        if (world.getRandom().nextDouble(10) <= 1.25f) {
            entity.equipStack(EquipmentSlot.HEAD, getTransBanner());
            entity.setEquipmentDropChance(EquipmentSlot.HEAD, 100.0f);
        }
    }
    default ItemStack getTransBanner() {
        ItemStack itemStack = new ItemStack(Items.WHITE_BANNER);
        NbtCompound nbtCompound = new NbtCompound();
        NbtList nbtList = (new BannerPattern.Patterns())
                .add(BannerPattern.HALF_HORIZONTAL, DyeColor.PINK)
                .add(BannerPattern.HALF_HORIZONTAL_MIRROR, DyeColor.PINK)
                .add(BannerPattern.STRIPE_TOP, DyeColor.LIGHT_BLUE)
                .add(BannerPattern.STRIPE_BOTTOM, DyeColor.LIGHT_BLUE)
                .add(BannerPattern.STRIPE_MIDDLE, DyeColor.WHITE)
                .toNbt();
        nbtCompound.put("Patterns", nbtList);
        BlockItem.setBlockEntityNbt(itemStack, BlockEntityType.BANNER, nbtCompound);
        itemStack.addHideFlag(ItemStack.TooltipSection.ADDITIONAL);
        itemStack.setCustomName(ChatFormatter.text("&dTrans rights bitch"));
        return itemStack;
    }

}
