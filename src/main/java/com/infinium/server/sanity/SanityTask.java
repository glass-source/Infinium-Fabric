package com.infinium.server.sanity;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class SanityTask {
    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Healthboost", 4, EntityAttributeModifier.Operation.ADDITION);
    private final SanityManager manager;
    public SanityTask(SanityManager manager){
        this.manager = manager;
    }
    
    public void run(MinecraftServer server){
        manager.totalPlayers.forEach((p) -> {
            if (shouldPreventEffects(p)) return;
            calcSanity(p);
            sanityEffects(p);
        });
    }

    private void sanityEffects(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (entityAttributeInstance == null) return;

        int sanity = manager.get(p, manager.SANITY);
        var world = p.getWorld();
        showKairosTime(p);

        if (sanity >= 90) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);

        } else {

            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

            var random = world.getRandom();
            if (sanity < 50) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.HUNGER, 120, 0));
            if (sanity < 35) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 120, 0));
            if (sanity < 25) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 200, random.nextInt(5)));
        }
    }

    private void showKairosTime(PlayerEntity p) {
        int timeCooldownTicks = manager.get(p, manager.TIME_COOLDOWN);
        var timeCooldownSeconds = timeCooldownTicks / 20;
        var timeCooldownMinutes = timeCooldownSeconds % 3600 / 60;
        var timeCooldownHours = timeCooldownSeconds % 86400 / 3600;
        timeCooldownSeconds %= 60;

        var color = timeCooldownTicks <= 5000 ? "&4" : "&6";
        var msg = ChatFormatter.text(color+ String.format("%02d:%02d:%02d", timeCooldownHours, timeCooldownMinutes, timeCooldownSeconds));
        p.sendMessage(msg, true);
    }

    private void calcSanity(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        int timeCooldownTicks = manager.get(p, manager.TIME_COOLDOWN);

        if (p.getHealth() >= p.getMaxHealth() - 4.0) manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);
        else if (p.getHealth() <= (p.getMaxHealth() * 0.5)) manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);

        if (timeCooldownTicks > 0) manager.decrease (p, 1, manager.TIME_COOLDOWN);
        if (timeCooldownTicks <= 5000) decreaseRandomSanity(p, 40);

        if (p.getAttacker() != null) decreaseRandomSanity(p, 80);
        else increaseRandomSanity(p, 120);

        if (hasNegativeEffects(p)) decreaseRandomSanity(p, 50);
        if (hasPositiveEffects(p)) increaseRandomSanity(p, 100);

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            manager.add(p, 1, manager.SANITY);
            manager.set(p, 120, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, 80, manager.NEGATIVE_HEALTH_COOLDOWN);
        }
    }

    private boolean hasNegativeEffects(PlayerEntity p) {
        return p.hasStatusEffect(StatusEffects.BLINDNESS) ||
               p.hasStatusEffect(StatusEffects.POISON) ||
               p.hasStatusEffect(StatusEffects.MINING_FATIGUE) ||
               p.hasStatusEffect(StatusEffects.WITHER) ||
               p.hasStatusEffect(InfiniumEffects.MADNESS);
    }

    private boolean hasPositiveEffects(PlayerEntity p) {
        return p.hasStatusEffect(StatusEffects.REGENERATION) ||
                p.hasStatusEffect(StatusEffects.SATURATION) ||
                p.hasStatusEffect(StatusEffects.ABSORPTION) ||
                p.hasStatusEffect(StatusEffects.RESISTANCE) ||
                p.hasStatusEffect(InfiniumEffects.IMMUNITY);
    }
    private void decreaseRandomSanity(PlayerEntity p, int randomChance) {
        if (p.getWorld().getRandom().nextInt(randomChance) == 1) manager.decrease(p, 1, manager.SANITY);
    }

    private void increaseRandomSanity(PlayerEntity p, int randomChance) {
        if (p.getWorld().getRandom().nextInt(randomChance) == 1) manager.add(p, 1, manager.SANITY);
    }

    private boolean shouldPreventEffects(PlayerEntity p) {
        return p.isCreative() || p.isSpectator() || p.isDead() || p.isCreativeLevelTwoOp() || p.getAbilities().creativeMode || p.getAbilities().invulnerable;
    }

}
