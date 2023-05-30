package com.infinium.server.items.custom.tools.utility;

import com.infinium.server.items.InfiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class GrapplingHookItem extends Item implements InfiniumItem, Vanishable {

    public GrapplingHookItem(Settings settings) {
        super(settings);
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        var itemStack = user.getStackInHand(hand);
        var vec = user.getRotationVector().add(user.getVelocity());

        if (user.fishHook != null) {
            if (!world.isClient) {
                user.fishHook.use(itemStack);
            }

            user.setVelocity(vec.getX() * 1.75f, vec.getY() * 2.5f, vec.getZ() * 1.75f);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_RETRIEVE, SoundCategory.NEUTRAL, 1.0F, 0.05f);
            world.emitGameEvent(user, GameEvent.FISHING_ROD_REEL_IN, user);
            itemStack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
            cooldownManager.set(this, 40);

        } else {
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_FISHING_BOBBER_THROW, SoundCategory.NEUTRAL, 1.0F, 0.05f);
            if (!world.isClient) {
                var hook = new FishingBobberEntity(user, world, 0, 0);
                world.spawnEntity(hook);
                hook.setNoGravity(true);
                hook.setGlowing(true);
            }
            world.emitGameEvent(user, GameEvent.FISHING_ROD_CAST, user);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }


}
