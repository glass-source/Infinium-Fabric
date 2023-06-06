package com.infinium.server.items.custom.tools.runes;

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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RuneItem extends ToolItem implements InfiniumItem {
    protected final StatusEffect statusEffect;
    protected final int effectDurationTicks;
    protected int cooldownTicks;
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
    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (entity instanceof ServerPlayerEntity user && !world.isClient()) {

            var inv = user.getInventory();
            List<Integer> cooldowns = new ArrayList<>();
            for (int i = 0; i < inv.size(); i++) {
                if (inv.getStack(i) != null) {
                    var iStack = inv.getStack(i);
                    if (iStack.getItem().equals(this)) {
                        var iData = iStack.getOrCreateNbt();
                        var iTicks = iData.getInt("cooldownTicks");
                        cooldowns.add(iTicks);
                    }
                }
            }

            Collections.sort(cooldowns);
            var data = stack.getOrCreateNbt();
            data.putInt("cooldownTicks", cooldowns.get(cooldowns.size() - 1) - 1);
        }
    }
    @Override
    public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return false;
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!world.isClient()) {
            var stack = user.getStackInHand(hand);
            var data = stack.getOrCreateNbt();
            var ticks = data.getInt("cooldownTicks");

            if (ticks <= 0) {
                var inv = user.getInventory();

                for (int i = 0; i < inv.size(); i++) {
                    if (inv.getStack(i) != null) {
                        var iStack = inv.getStack(i);
                        if (iStack.getItem().equals(this)) {
                            var iData = iStack.getOrCreateNbt();
                            iData.putInt("cooldownTicks", cooldownTicks);
                        }
                    }
                }

                user.addStatusEffect(new StatusEffectInstance(this.statusEffect, effectDurationTicks, amplifier));
                user.playSound(SoundEvents.ENTITY_ILLUSIONER_PREPARE_BLINDNESS, SoundCategory.AMBIENT, 1, 0.03F);
            } else {
                var msg = getCooldownMsg(stack);
                user.sendMessage(ChatFormatter.textWithPrefix(msg), false);
            }
        }

        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        var data = stack.getOrCreateNbt();
        var ticks = data.getInt("cooldownTicks");
        return ticks <= 0;
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
        var data = stack.getOrCreateNbt();
        var ticks = data.getInt("cooldownTicks");
        if (ticks <= 0) return "&7No cooldown";
        int timeCooldownSeconds = ticks / 20;
        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var formattedSeconds = timeCooldownSeconds % 60;
        return ChatFormatter.format("&7Cooldown en " + getRuneName(this.toString()) + "&7: [" + "&6" + String.format("%02d:%02d", timeCooldownMinutes, formattedSeconds) + "&7]");
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(ChatFormatter.text(getCooldownMsg(stack)));
        appendGeneralToolTip(stack, tooltip, 2);
    }
}
