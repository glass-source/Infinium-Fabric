package me.mrswz.items.custom.misc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class GrapplingHook extends FishingRodItem {

    public GrapplingHook(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            var cooldownManager = user.getItemCooldownManager();
            if (!cooldownManager.isCoolingDown(this)) {
                Vec3d vec = user.getRotationVector().multiply(2.455D);
                user.setVelocity(vec.getX() * 1.25, vec.getY() * 1.25, vec.getZ() * 1.25);
                user.playSound(SoundEvents.ENTITY_FISHING_BOBBER_THROW, 10, 1);
                cooldownManager.set(this, 20);
                return super.use(world, user, hand);
            }

        }
        return null;
    }
}
