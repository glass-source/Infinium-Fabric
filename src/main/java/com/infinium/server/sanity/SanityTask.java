package com.infinium.server.sanity;

import com.infinium.global.utils.ChatFormatter;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class SanityTask {

    private final SanityManager manager;


    public SanityTask(SanityManager manager){
        this.manager = manager;
    }
    
    public void run(){
        manager.totalPlayers.forEach((p) -> {
            if (!p.isSpectator() || !p.isCreative()) {
                sanityEffects(p);
                sanityDeBuffs(p);
            }
        });
    }

    private void sanityDeBuffs(ServerPlayerEntity p) {
        var world = p.getWorld();

        if (world.getRandom().nextInt(500) == 1) manager.decrease(p, 1, manager.TIME_COOLDOWN);

        if (manager.get(p, manager.TIME_COOLDOWN) <= 0) {
            if (p.getWorld().getRandom().nextInt(10) == 1) manager.decrease(p, 1, manager.SANITY);
        }

    }

    private void sanityEffects(ServerPlayerEntity p) {
        if (p.getHealth() >= p.getMaxHealth() - 3.0D) manager.decrease(p, 1, manager.POSITIVE_HEALTH_COOLDOWN);

        if (p.getHealth() <= (p.getMaxHealth() / 2)) manager.decrease(p, 1, manager.NEGATIVE_HEALTH_COOLDOWN);

        if (manager.get(p, manager.POSITIVE_HEALTH_COOLDOWN) <= 0) {
            manager.add(p, 1, manager.SANITY);
            manager.set(p, (20 * 60) * 5, manager.POSITIVE_HEALTH_COOLDOWN);
        }

        if (manager.get(p, manager.NEGATIVE_HEALTH_COOLDOWN) <= 0) {
            manager.decrease(p, 1, manager.SANITY);
            manager.set(p, (20 * 60) * 5, manager.NEGATIVE_HEALTH_COOLDOWN);
        }

    }


}
