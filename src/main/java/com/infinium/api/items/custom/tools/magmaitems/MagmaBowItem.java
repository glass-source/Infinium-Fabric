package com.infinium.api.items.custom.tools.magmaitems;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagmaBowItem extends BowItem {

    public MagmaBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return true;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            boolean canPickUpArrow = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = playerEntity.getArrowType(stack);

            if (!itemStack.isEmpty() || canPickUpArrow) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(Items.ARROW);
                }

                int useTime = this.getMaxUseTime(stack) - remainingUseTicks;
                float pullProgress = getPullProgress(useTime);
                if (!((double) pullProgress < 0.1)) {
                    boolean checkCanPickUp = canPickUpArrow && itemStack.isOf(Items.ARROW);
                    if (!world.isClient) {

                        ArrowItem arrowItem = (ArrowItem)(itemStack.getItem() instanceof ArrowItem ? itemStack.getItem() : Items.ARROW);
                        ArrowEntity arrowEntity = (ArrowEntity) arrowItem.createArrow(world, itemStack, playerEntity);
                        arrowEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, pullProgress * 3.0F, 1.0F);
                        arrowEntity.setCritical(true);
                        arrowEntity.setDamage(arrowEntity.getDamage() * 2);
                        arrowEntity.addEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 4));
                        arrowEntity.addEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 4));
                        arrowEntity.addEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));

                        int powerLevel = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                        if (powerLevel > 0) {
                            arrowEntity.setDamage(arrowEntity.getDamage() + (double) powerLevel * 0.5 + 0.5);
                        }

                        int punchLevel = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                        if (punchLevel > 0) {
                            arrowEntity.setPunch(punchLevel);
                        }

                        if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) arrowEntity.setOnFireFor(120);

                        if (checkCanPickUp || playerEntity.getAbilities().creativeMode && (itemStack.isOf(Items.SPECTRAL_ARROW) || itemStack.isOf(Items.TIPPED_ARROW))) {
                            arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        world.spawnEntity(arrowEntity);
                    }

                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + pullProgress * 0.5F);
                    if (!checkCanPickUp && !playerEntity.getAbilities().creativeMode) {
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            playerEntity.getInventory().removeOne(itemStack);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }
}
