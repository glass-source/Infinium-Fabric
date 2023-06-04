package com.infinium.server.items.custom.tools.runes;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.items.InfiniumItem;
import com.infinium.server.items.materials.InfiniumToolMaterials;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.server.MinecraftServer;
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
    protected MinecraftServer server;
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

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            this.server = Infinium.getInstance().getCore().getServer();
            var stack = user.getStackInHand(hand);
            var data = stack.getOrCreateNbt();
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
                var msg = getCooldownMsg(stack);
                user.sendMessage(ChatFormatter.textWithPrefix(msg), false);
            }
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }
    public String getRuneName(String runeType) {

        switch (runeType) {
            case "immunity_rune" -> {return "&dImmunity Rune";}
            case "resistance_rune" -> {return "&5Resistance Rune";}
            case "speed_rune" -> {return "&bSpeed Rune";}
            case "fire_rune" -> {return "&cFire Rune";}
            default -> {return "";}
        }
    }
    public String getCooldownMsg(ItemStack stack) {
        int cooldownTicks = this.getCurrentCooldown(stack);
        int timeCooldownSeconds = cooldownTicks / 20;
        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var formattedSeconds = timeCooldownSeconds % 60;
        return ChatFormatter.format("&7Cooldown en " + getRuneName(this.toString()) + "&7: [" + "&6" + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (this.server != null) tooltip.add(ChatFormatter.text("Cooldown: " + this.getCurrentCooldown(stack)));

        appendGeneralToolTip(stack, tooltip, 2);
    }
}
