package com.infinium.server.items.custom.tools.runes;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.items.InfiniumItem;
import com.infinium.server.items.materials.InfiniumToolMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RuneItem extends ToolItem implements InfiniumItem {
    protected final StatusEffect statusEffect;
    protected final int effectDurationTicks;
    protected final int cooldownTicks;
    protected final int amplifier;
    public RuneItem(Settings settings, StatusEffect statusEffect, int effectDurationTicks, int cooldownTicks) {
        super(InfiniumToolMaterials.VOID, settings);
        this.statusEffect = statusEffect;
        this.effectDurationTicks = effectDurationTicks;
        this.cooldownTicks = cooldownTicks;
        this.amplifier = 0;
    }
    public RuneItem(Settings settings, StatusEffect statusEffect, int effectDurationTicks, int cooldownTicks, int amplifier) {
        super(InfiniumToolMaterials.VOID, settings);
        this.statusEffect = statusEffect;
        this.effectDurationTicks = effectDurationTicks;
        this.cooldownTicks = cooldownTicks;
        this.amplifier = amplifier;
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            var data = ((EntityDataSaver) user).getPersistentData();
            var cooldownString = "infinium.cooldown." + this;
            int cooldownTicks = data.getInt(cooldownString) - Infinium.getInstance().getCore().getServer().getTicks();

            if (cooldownTicks <= 0) {
                var startingTick = Infinium.getInstance().getCore().getServer().getTicks();
                var endingTick = startingTick + this.cooldownTicks;
                var startingTickString = "infinium.cooldown.start." + this;

                data.putInt(startingTickString, startingTick);
                data.putInt(cooldownString, endingTick);
                user.addStatusEffect(new StatusEffectInstance(this.statusEffect, effectDurationTicks, amplifier));
                user.playSound(SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.AMBIENT, 1, 0.03F);

            } else {
                int timeCooldownSeconds = cooldownTicks / 20;
                var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
                var formattedSeconds = timeCooldownSeconds % 60;
                var msg = ChatFormatter.textWithPrefix("&7Cooldown en " + getRuneName(this.toString()) + "&7: [" + "&6" + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");
                user.sendMessage(msg, false);
            }
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
    private String getRuneName(String runeType) {

        switch (runeType) {
            case "immunity_rune" -> {return "&dImmunity Rune";}
            case "resistance_rune" -> {return "&5Resistance Rune";}
            case "speed_rune" -> {return "&bSpeed Rune";}
            case "fire_rune" -> {return "&cFire Rune";}
            default -> {return "";}
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        appendGeneralToolTip(stack, tooltip, 2);
        super.appendTooltip(stack, world, tooltip, context);
    }
    public int getTotalCooldown(PlayerEntity user) {
        var data = ((EntityDataSaver) user).getPersistentData();
        var startingTickString = "infinium.cooldown.start." + this;
        return getCooldownTicks() - data.getInt(startingTickString);
    }
    public int getCooldownTicks() {return this.cooldownTicks;}
}
