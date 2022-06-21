package com.infinium.api.items.custom.tools.runes;

import com.infinium.Infinium;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.concurrent.TimeUnit;

public class WitherRuneItem extends ToolItem {

    public WitherRuneItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!cooldownManager.isCoolingDown(this)) {
            if (!world.isClient()) {
                Infinium.getExecutor().schedule(() -> cooldownManager.set(this, 15), 75, TimeUnit.MILLISECONDS);
                WitherSkullEntity skull = new WitherSkullEntity(world, user, 0, 0,0);
                Vec3d vec = user.getRotationVector();
                skull.setPosition(user.getEyePos());
                skull.setVelocity(vec.getX() * 1.25, vec.getY() * 1.25, vec.getZ() * 1.25);
                world.spawnEntity(skull);
                Infinium.getExecutor().schedule(() -> {
                    if (skull.isAlive()) {
                        world.createExplosion(skull, skull.getX(), skull.getY(), skull.getZ(), 3, Explosion.DestructionType.DESTROY);
                        skull.remove(Entity.RemovalReason.KILLED);
                        skull.kill();
                    }
                }, 4, TimeUnit.SECONDS);
            }

            if (user.getEquippedStack(EquipmentSlot.MAINHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.MAINHAND), world, user);
            } else if (user.getEquippedStack(EquipmentSlot.OFFHAND).getItem().equals(this)) {
                finishUsing(user.getEquippedStack(EquipmentSlot.OFFHAND), world, user);
            }
            world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 0.75F);
        }
        return super.use(world, user, hand);
    }


}
