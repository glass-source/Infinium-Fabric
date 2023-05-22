package com.infinium.server.items.custom.tools.runes;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RuneItem extends ToolItem implements InfiniumItem {

    protected final StatusEffect statusEffect;
    protected final int effectDurationTicks;
    protected final int cooldownTicks;
    protected final int amplifier;
    public RuneItem(ToolMaterial material, Settings settings, StatusEffect statusEffect, int duration, int cooldown) {
        super(material, settings);
        this.statusEffect = statusEffect;
        this.effectDurationTicks = duration;
        this.cooldownTicks = cooldown;
        this.amplifier = 0;
    }
    public RuneItem(ToolMaterial material, Settings settings, StatusEffect statusEffect, int effectDurationTicks, int cooldownTicks, int amplifier) {
        super(material, settings);
        this.statusEffect = statusEffect;
        this.effectDurationTicks = effectDurationTicks;
        this.cooldownTicks = cooldownTicks;
        this.amplifier = amplifier;
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        var data = ((EntityDataSaver) user).getPersistentData();
        var cooldownString = "infinium.cooldown." + this;
        int cooldownTicks;
        if (!world.isClient() && world.getServer() != null) {

            cooldownTicks = data.getInt(cooldownString) - world.getServer().getTicks();

            if (cooldownTicks <= 0) {
                user.addStatusEffect(new StatusEffectInstance(this.statusEffect, effectDurationTicks, amplifier));
                user.playSound(SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.AMBIENT, 1, 0.03F);
                setCooldown(user, this, this.cooldownTicks);
            } else {
                int timeCooldownSeconds = cooldownTicks / 20;
                var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
                var formattedSeconds = timeCooldownSeconds % 60;
                var msg = ChatFormatter.textWithPrefix("Cooldown: " + "&7[" + "&6" + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");
                user.sendMessage(msg, false);
            }

        }
        return super.use(world, user, hand);
    }


}
