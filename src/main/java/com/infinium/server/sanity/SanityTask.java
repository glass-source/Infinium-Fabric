package com.infinium.server.sanity;

import com.infinium.global.utils.DateUtils;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.items.custom.armor.MagmaArmorItem;
import com.infinium.server.items.custom.armor.VoidArmorItem;
import com.infinium.server.items.custom.tools.voiditems.VoidAxeItem;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;

public class SanityTask {

    private final SanityManager manager;


    public SanityTask(SanityManager manager){
        this.manager = manager;
    }
    
    public void run(){
        manager.totalPlayers.forEach((p) -> {
            if (!p.isSpectator() || !p.isCreative()) {
                calcSanity(p);
                sanityDeBuffs(p);
            }
        });
    }

    private void sanityDeBuffs(ServerPlayerEntity p) {
        int sanity = manager.get(p, manager.SANITY);
        var world = p.getWorld();

        if (sanity < 50) if (world.getRandom().nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 160, 0));
        if (sanity < 40) if (world.getRandom().nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 160, 0));
        if (sanity < 30) if (world.getRandom().nextInt(250) == 1) p.addStatusEffect(new StatusEffectInstance(InfiniumEffects.MADNESS, 160, 0));

    }

    private void calcSanity(ServerPlayerEntity p) {
        var world = p.getWorld();

        if (p.getHealth() >= p.getMaxHealth() - 3.0D) manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);

        if (p.getHealth() <= (p.getMaxHealth() * 0.5)) manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            manager.add(p, 1, manager.SANITY);
            manager.set(p, (20 * 60) * 5, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, (20 * 60) * 5, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

        if (world.getRandom().nextInt(500) == 1) manager.decrease(p, 1, manager.TIME_COOLDOWN);

        if (manager.get(p, manager.TIME_COOLDOWN) <= 0) {
            if (p.getWorld().getRandom().nextInt(10) == 1) manager.decrease(p, 1, manager.SANITY);
        }
    }



}
