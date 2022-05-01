package com.infinium.api.items.custom.runes;

import com.infinium.api.effects.InfiniumEffects;
import net.kyori.adventure.audience.Audience;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class ImmunityRuneItem extends Item {

    public ImmunityRuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!cooldownManager.isCoolingDown(this)) {
            world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.PLAYERS, 1, 0.03F);
            if (!world.isClient()) {
                user.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 25));
                cooldownManager.set(this, 20 * (60 * 2));
            }
        }
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (user instanceof PlayerEntity playerEntity) {
            if (!world.isClient()) stack.damage(1, playerEntity, (p) -> p.sendToolBreakStatus(user.getActiveHand()));
        }
        return super.finishUsing(stack, world, user);
    }

}
