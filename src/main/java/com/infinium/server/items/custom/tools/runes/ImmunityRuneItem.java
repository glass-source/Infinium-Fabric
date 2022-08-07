package com.infinium.server.items.custom.tools.runes;

import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ImmunityRuneItem extends ToolItem {

    public ImmunityRuneItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!cooldownManager.isCoolingDown(this)) {
            if (!world.isClient()) {
                user.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 25));
                cooldownManager.set(this, 20 * (60 * 2));
            }

            if (user.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.MAINHAND), world, user);
            } else if (user.getEquippedStack(EquipmentSlot.OFFHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.OFFHAND), world, user);
            }
            world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.PLAYERS, 1, 0.03F);
        }
        return super.use(world, user, hand);
    }

}
