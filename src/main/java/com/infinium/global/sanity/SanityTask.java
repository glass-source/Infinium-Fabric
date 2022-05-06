package com.infinium.global.sanity;

import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.utils.EntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.GameMode;

import java.util.ArrayList;

import static com.infinium.global.sanity.SanityManager.*;

public class SanityTask {

    private static int healthCooldown = 10;
    private static int negativeHealthCooldown = 5;
    private static int biomeCooldown = 20;

    public static void run(){



        totalPlayers.forEach((player) -> {

            if (!player.isSpectator()) {
                calcSanity(player);
                sanityEffects(player);
                sanityDebuffs(player);
            }
        });

    }

    private static void sanityDebuffs(PlayerEntity player) {

    }

    private static void sanityEffects(PlayerEntity p) {

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

    private static void calcSanity(PlayerEntity player) {
        NbtCompound data = ((EntityDataSaver) player).getPersistentData();
        int cordura = data.getInt("infinium.cordura");
        player.sendMessage(ChatFormatter.text("&8[&1Cordura&8]: &5" + cordura + "%"), true);
    }




}
