package com.infinium.server.sanity;

import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.entities.InfiniumEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.server.network.ServerPlayerEntity;

import   com.infinium.server.sanity.SanityManager.*;

public class SanityTask {

    private final SanityManager manager;

    public SanityTask(SanityManager manager){
        this.manager = manager;
    }
    
    public void run(){
        manager.totalPlayers.forEach((player) -> {
            if (!player.isSpectator()) {
                calcSanity(player);
                sanityEffects(player);
                sanityDeBuffs(player);
            }
        });

    }

    private void sanityDeBuffs(ServerPlayerEntity player) {

    }

    private void sanityEffects(ServerPlayerEntity p) {
        int healthCooldown = 10;
        int negativeHealthCooldown = 5;
        int biomeCooldown = 20;

        if (p.getHealth() >= p.getMaxHealth() - 4.0D) healthCooldown--;

        if (p.getHealth() <= (p.getMaxHealth() / 2)) negativeHealthCooldown--;

        if (healthCooldown <= 0) {
            manager.addSanity(p, 2);
            healthCooldown = 10;
        }

        if (negativeHealthCooldown <= 0) {
            manager.removeSanity(p, 2);
            negativeHealthCooldown = 10;
        }

    }

    private void calcSanity(ServerPlayerEntity player) {
        player.sendMessage(ChatFormatter.text("&8[&1Cordura&8]: &5" + manager.getSanity(player) + "%"), true);
    }

}
