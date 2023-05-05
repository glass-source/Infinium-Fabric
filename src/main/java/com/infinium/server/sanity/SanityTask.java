package com.infinium.server.sanity;

import com.infinium.server.effects.InfiniumEffects;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class SanityTask {
    private static final EntityAttributeModifier EXTRA_HEALTH_BOOST = new EntityAttributeModifier(UUID.randomUUID(), "Sanity Healthboost", 4, EntityAttributeModifier.Operation.ADDITION);;
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
        int sanity = manager.get(p, manager.SANITY);
        var world = p.getWorld();
        var entityAttributeInstance = p.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

        if (sanity >= 90) {
            if (!entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) entityAttributeInstance.addTemporaryModifier(EXTRA_HEALTH_BOOST);

        } else {
            if (entityAttributeInstance.hasModifier(EXTRA_HEALTH_BOOST)) {
                entityAttributeInstance.removeModifier(EXTRA_HEALTH_BOOST);
                p.setHealth(p.getHealth());
            }

            var random = world.getRandom();
            if (sanity < 50) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 0));
            if (sanity < 40) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 160, 0));
            if (sanity < 30) if (random.nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 160, random.nextInt(5)));
        }
    }

    private void calcSanity(PlayerEntity p) {
        if (shouldPreventEffects(p)) return;
        var worldRandom = p.getWorld().getRandom();

        if (p.getHealth() >= p.getMaxHealth() - 3.0D) manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);

        if (p.getHealth() <= (p.getMaxHealth() * 0.5)) manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            manager.add(p, 1, manager.SANITY);
            manager.set(p, 6000, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, 6000, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (worldRandom.nextInt(500) == 1) manager.decrease(p, 1, manager.TIME_COOLDOWN);

        if (manager.get(p, manager.TIME_COOLDOWN) <= 0) {
            if (worldRandom.nextInt(100) == 1) {
                manager.decrease(p, 1, manager.SANITY);
            }

        }
    }

    private boolean shouldPreventEffects(PlayerEntity p) {
        return p.isCreative() || p.isSpectator() || p.isDead() || p.isCreativeLevelTwoOp() || p.getAbilities().creativeMode || p.getAbilities().invulnerable;
    }

}
