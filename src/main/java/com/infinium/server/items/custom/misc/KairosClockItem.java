package com.infinium.server.items.custom.misc;

import com.infinium.Infinium;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class KairosClockItem extends Item implements InfiniumItem {
    private final List<PlayerState> stateList;
    public KairosClockItem(Settings settings) {
        super(settings);
        stateList = new ArrayList<>();
    }
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity pl && pl.getServer() != null && !world.isClient()) {
            stateList.add(new PlayerState(pl));
        }
    }
    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getServer() != null) {
            if (stateList.size() < (20 * 12)) return super.use(world, user, hand);
            PlayerState state = stateList.get(stateList.size() - (20 * 12));
            if (state != null) {
                var manager = Infinium.getInstance().getCore().getSanityManager();
                var pos = state.getPos();
                var health = state.getHealth();
                var hunger = state.getHunger();
                var sanity = state.getSanity();
                var itemStack = user.getStackInHand(hand);

                user.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 35, 0));
                user.setHealth(health);
                user.getHungerManager().setFoodLevel(hunger);
                user.teleport(pos.getX(), pos.getY(), pos.getZ());
                user.getItemCooldownManager().set(this, 20 * 12);
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.NEUTRAL, 1.0F, 0.2f);

                itemStack.damage(1, user, (p) -> p.sendToolBreakStatus(hand));
                manager.set(user, sanity, manager.SANITY);
                return TypedActionResult.success(itemStack, world.isClient());
            }
        }

        return super.use(world, user, hand);
    }

    private static class PlayerState {
        private final BlockPos pos;
        private final int hunger;
        private final float health;
        private final int sanity;
        private PlayerState(PlayerEntity player) {
            var manager = Infinium.getInstance().getCore().getSanityManager();
            this.pos = player.getBlockPos();
            this.hunger = player.getHungerManager().getFoodLevel();
            this.health = player.getHealth();
            this.sanity = manager.get(player, manager.SANITY);
        }
        public BlockPos getPos() {
            return pos;
        }
        public int getHunger() {
            return hunger;
        }
        public float getHealth() {
            return health;
        }
        public int getSanity() {
            return sanity;
        }
    }

}
