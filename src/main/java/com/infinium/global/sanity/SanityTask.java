package com.infinium.global.sanity;

import com.infinium.api.utils.ChatFormatter;
import net.minecraft.server.network.ServerPlayerEntity;

import static com.infinium.global.sanity.SanityManager.*;

public class SanityTask {

    private static int healthCooldown = 10;
    private static int negativeHealthCooldown = 5;
    private static int biomeCooldown = 20; //Private field 'biomeCooldown' is never used   intellij pensando

    public static void run(){



        totalPlayers.forEach((player) -> {

            if (!player.isSpectator()) {
                calcSanity(player);
                sanityEffects(player);
                sanityDeBuffs(player);
            }
        });

    }

    private static void sanityDeBuffs(ServerPlayerEntity player) {

    }

    private static void sanityEffects(ServerPlayerEntity p) {

        if (p.getHealth() >= p.getMaxHealth() - 4.0D) healthCooldown--;

        if (p.getHealth() <= (p.getMaxHealth() / 2)) negativeHealthCooldown--;

        if (healthCooldown <= 0) {
            addSanity(p, 2);
            healthCooldown = 10;
        }

        if (negativeHealthCooldown <= 0) {
            removeSanity(p, 2);
            negativeHealthCooldown = 10;
        }

    }

    private static void calcSanity(ServerPlayerEntity player) {

        player.sendMessage(ChatFormatter.text("&8[&1Cordura&8]: &5" + SanityManager.getSanity(player) + "%"), true);
    }




}
