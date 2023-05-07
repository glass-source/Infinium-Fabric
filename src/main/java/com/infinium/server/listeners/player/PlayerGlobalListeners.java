package com.infinium.server.listeners.player;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.effects.InfiniumEffects;
import com.infinium.server.world.dimensions.InfiniumDimensions;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Random;
import java.util.SplittableRandom;

public class PlayerGlobalListeners {

    private final Infinium instance;
    private final InfiniumServerManager core;

    public PlayerGlobalListeners(Infinium instance) {
        this.instance = instance;
        this.core = instance.getCore();
    }
    public void registerListeners(){
        playerBedCallback();
    }

    private void playerBedCallback() {
        EntitySleepEvents.START_SLEEPING.register((entity, sleepingPos) -> {
            if (Infinium.getInstance().getDateUtils() == null) return;
            var day = Infinium.getInstance().getDateUtils().getCurrentDay();
            if (day < 7) return;
            if (!(entity instanceof ServerPlayerEntity p)) return;
            var name = p.getEntityName();
            var random = new Random().nextInt(100);
            if (day < 35) {
                if (random < day) {
                    tpToWorld(InfiniumDimensions.THE_NIGHTMARE, p, 2000);
                    ChatFormatter.broadcastMessageWithPrefix("&7El jugador &6&l" + name + " &7ha entrado a la &4&lNightmare &7" + "\nProbabilidad: " + random + " < " + day);
                } else {
                    p.sendMessage(ChatFormatter.textWithPrefix("&7Has Dormido. Probabilidad de ir a la &4&lNightmare&7: " + random + " > " + day), false);
                }
            } else {
                if (random < day / 2) {
                    tpToWorld(InfiniumDimensions.THE_NIGHTMARE, p, 5000);
                    ChatFormatter.broadcastMessageWithPrefix("&7El jugador &6&l" + name + " &7ha entrado a la &4&lNightmare &7" + "\nProbabilidad: " + random + " < " + day / 2);
                } else {
                    p.sendMessage(ChatFormatter.textWithPrefix("&7Has Dormido. Probabilidad de ir a la &4&lNightmare&7: " + random + " > " + day / 2), false);
                }
            }

        });
    }

    private void tpToWorld(RegistryKey<World> destination, ServerPlayerEntity who, int maxToTP) {
        if (who.getServer() == null) return;
        if (who.getServer().getWorld(destination) == null) return;

        var world = who.getWorld();
        var destinationWorld = world.getServer().getWorld(destination);
        SplittableRandom random = new SplittableRandom();
        int x = (random.nextBoolean() ? 1 : -1) * random.nextInt(maxToTP);
        int z = (random.nextBoolean() ? 1 : -1) * random.nextInt(maxToTP);
        who.wakeUp(false, true);
        who.teleport(destinationWorld, x, 101.05f, z, who.getYaw(), who.getPitch());
        who.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 20 * 30, 0));
        who.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 20 * 30, 3));
        who.addStatusEffect(new StatusEffectInstance(InfiniumEffects.IMMUNITY, 20 * 14, 0));
    }

}
