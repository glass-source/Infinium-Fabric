package com.infinium.api.items.custom.tools.utility;

import com.infinium.api.utils.ChatFormatter;
import net.kyori.adventure.sound.Sound;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class GrapplingHookItem extends FishingRodItem {

    public GrapplingHookItem(Settings settings) {
        super(settings);
    }

    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            var cooldownManager = user.getItemCooldownManager();
            if (!cooldownManager.isCoolingDown(this)) {
                Vec3d vec = user.getRotationVector();
                user.setVelocity(vec.getX() * 2.25, vec.getY() * 3.25, vec.getZ() * 2.25);
                cooldownManager.set(this, 20);

                if (user.getStackInHand(hand).getItem().equals(this)) {
                    user.playSound(SoundEvents.ENTITY_FISHING_BOBBER_THROW, 10, 0.5f);
                    super.use(world, user, hand);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            }
        }
        return TypedActionResult.fail(this.getDefaultStack());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(new TranslatableText("item.infinium.grappling_hook.tooltip.shift"));
        } else {
            tooltip.add(new TranslatableText("item.infinium.grappling_hook.tooltip"));
        }
    }
}
