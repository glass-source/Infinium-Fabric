package com.infinium.api.items.custom.runes;

import com.infinium.api.effects.InfiniumEffects;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WitherRuneItem extends Item {

    public WitherRuneItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!cooldownManager.isCoolingDown(this)) {
            world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 0.75F);
            if (!world.isClient()) {
                this.getDefaultStack().damage(1, user, (p) -> p.sendToolBreakStatus(user.getActiveHand()));
                cooldownManager.set(this, 15);
                WitherSkullEntity skull = new WitherSkullEntity(world, user, 0, 0,0);
                skull.setPosition(user.getEyePos());
                skull.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F + 1 * 0.5F, 1.0F);
                world.spawnEntity(skull);
            }
        }
        return super.use(world, user, hand);
    }


}
