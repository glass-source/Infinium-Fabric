package com.infinium.api.items.custom.tools.magmaitems;

import com.infinium.api.entities.projectiles.MagmaTridentEntity;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.ShulkerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class MagmaTridentItem extends TridentItem {

    EntityType<? extends MagmaTridentEntity> type;

    public MagmaTridentItem(Settings settings) {
        super(settings);
    }

    public MagmaTridentItem(Settings settings, EntityType<? extends MagmaTridentEntity> entityType) {
        super(settings);
        this.type = entityType;
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    public EntityType<? extends MagmaTridentEntity> getEntityType() {
        return type;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 10) {

                int riptideLevel = EnchantmentHelper.getRiptide(stack);

                if (riptideLevel <= 0) {
                    if (!world.isClient()) {
                        if (riptideLevel == 0) {
                            MagmaTridentEntity magmaTridentEntity = new MagmaTridentEntity(world, playerEntity, stack);
                            magmaTridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + riptideLevel * 0.5F, 1.0F);
                            world.spawnEntity(magmaTridentEntity);
                            world.playSoundFromEntity(null, magmaTridentEntity, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F);

                            if (playerEntity.getAbilities().creativeMode) {
                                magmaTridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                            } else {
                                playerEntity.getInventory().removeOne(stack);
                            }

                        }
                    }
                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                } else {
                    if (playerEntity.isOnFire() || playerEntity.isTouchingWaterOrRain()) {
                        float f = playerEntity.getYaw();
                        float g = playerEntity.getPitch();
                        float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float k = -MathHelper.sin(g * 0.017453292F);
                        float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                        float m = MathHelper.sqrt(h * h + k * k + l * l);
                        float n = 3.0F * ((1.0F + riptideLevel) / 4.0F);
                        h *= n / m;
                        k *= n / m;
                        l *= n / m;
                        playerEntity.addVelocity(h, k, l);
                        playerEntity.useRiptide(20);
                        if (playerEntity.isOnGround()) {
                            float o = 1.1999999F;
                            playerEntity.move(MovementType.SELF, new Vec3d(0.0D, o, 0.0D));
                        }

                        SoundEvent soundEvent3;
                        if (riptideLevel >= 3) {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                        } else if (riptideLevel == 2) {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                        } else {
                            soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                        }

                        world.playSoundFromEntity(null, playerEntity, soundEvent3, SoundCategory.PLAYERS, 1.0F, 0.04F);
                    }
                }

            }
        }
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            if (target instanceof EndermanEntity || target instanceof ShulkerEntity) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 4));
            } else {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 160, 0));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 160, 4));
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 160, 4));
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }



}
