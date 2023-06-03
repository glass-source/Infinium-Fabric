package com.infinium.server.items.custom.tools.magmaitems;

import com.infinium.server.entities.InfiniumEntityType;
import com.infinium.server.entities.projectiles.MagmaTridentEntity;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagmaTridentItem extends TridentItem implements InfiniumItem {
    EntityType<? extends MagmaTridentEntity> type;
    public MagmaTridentItem(Settings settings) {
        super(settings);
        this.type = InfiniumEntityType.MAGMA_TRIDENT;
    }
    public EntityType<? extends MagmaTridentEntity> getEntityType() {
        return InfiniumEntityType.MAGMA_TRIDENT;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity playerEntity) {
            int useTimeTicks = this.getMaxUseTime(stack) - remainingUseTicks;

            if (useTimeTicks >= 10) {
                int riptideLevel = EnchantmentHelper.getRiptide(stack);

                if (riptideLevel <= 0) {
                    if (!world.isClient()) stack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                    if (!world.isClient()) {
                        if (riptideLevel == 0) {
                            MagmaTridentEntity magmaTridentEntity = new MagmaTridentEntity(world, playerEntity, stack);
                            magmaTridentEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + riptideLevel * 0.5F, 1.0F);
                            world.spawnEntity(magmaTridentEntity);
                            world.playSoundFromEntity(null, magmaTridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
                        float playerYaw = playerEntity.getYaw();
                        float playerPitch = playerEntity.getPitch();
                        float h = -MathHelper.sin(playerYaw * 0.0175F) * MathHelper.cos(playerPitch * 0.0175F);
                        float k = -MathHelper.sin(playerPitch * 0.0175F);
                        float l = MathHelper.cos(playerYaw * 0.0175F) * MathHelper.cos(playerPitch * 0.0175F);
                        float m = MathHelper.sqrt(h * h + k * k + l * l);
                        float n = 3.0F * ((1.0F + riptideLevel) / 4.0F);
                        h *= n / m;
                        k *= n / m;
                        l *= n / m;
                        playerEntity.addVelocity(h, k, l);
                        playerEntity.useRiptide(40);
                        if (!world.isClient()) stack.damage(1, user, p -> p.sendToolBreakStatus(user.getActiveHand()));
                        world.playSoundFromEntity(null, playerEntity, SoundEvents.ITEM_TRIDENT_RIPTIDE_3, SoundCategory.PLAYERS, 1.0F, 0.04F);
                        if (playerEntity.isOnGround()) playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.2f, 0.0D));
                    }
                }
            }
        }
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return fromMagmaToolHit(stack, target, attacker);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);

        } else if (EnchantmentHelper.getRiptide(itemStack) > 0 && !canUseRiptide(user)) {
            return TypedActionResult.fail(itemStack);

        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }
    private boolean canUseRiptide(PlayerEntity user) {
        return user.isOnFire() || user.isTouchingWaterOrRain();
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendInfiniumToolTip(tooltip, "magma", 2);
        super.appendTooltip(stack, world, tooltip, context);
    }

}
