package com.infinium.server.items.custom.tools.runes;

import com.infinium.server.entities.mobs.hostile.bosses.SuperNovaSkullEntity;
import com.infinium.server.items.InfiniumItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WitherRuneItem extends ToolItem implements InfiniumItem {
    public WitherRuneItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var cooldownManager = user.getItemCooldownManager();
        if (!world.isClient()) {
            cooldownManager.set(this, 15);
            Vec3d vec = user.getRotationVector();
            SuperNovaSkullEntity skull = new SuperNovaSkullEntity(world, user, vec.getX() * 1.5, vec.getY() * 1.5, vec.getZ() * 1.5);
            skull.setPosition(user.getEyePos());
            skull.setVelocity(vec.getX() * 1.5, vec.getY() * 1.5, vec.getZ() * 1.5);
            skull.setOwner(user);
            world.spawnEntity(skull);
        }
        world.playSound(user, user.getBlockPos(), SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.PLAYERS, 1, 0.45F);

        return super.use(world, user, hand);
    }


}
