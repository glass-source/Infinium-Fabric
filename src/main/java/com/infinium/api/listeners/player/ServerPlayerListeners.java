package com.infinium.api.listeners.player;

import com.infinium.Infinium;
import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.api.events.players.ServerPlayerConnectionEvents;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.utils.Animation;
import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.utils.EntityDataSaver;
import com.infinium.global.sanity.SanityManager;
import com.infinium.global.sanity.SanityTask;
import com.infinium.global.sounds.InfiniumSounds;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;

import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

public class ServerPlayerListeners {

    public static void registerListener(){
        playerConnectCallback();
        playerDisconnectCallback();
        playerDeathCallback();
    }

    private static void onEntityDeath(){

    }

    private static void playerDeathCallback(){

        ServerPlayerEvents.ALLOW_DEATH.register((player, damageSource, damageAmount) -> {
            boolean[] hasTotem = {false};

            for(ItemStack stack : player.getItemsHand()) {
                var item = stack.getItem();

                if (item.equals(Items.TOTEM_OF_UNDYING) || item.equals(InfiniumItems.VOID_TOTEM)) {
                    if(item.equals(Items.TOTEM_OF_UNDYING) && damageSource.isOutOfWorld()) {
                        continue;
                    }
                    hasTotem[0] = true;
                }
            }

            if (!hasTotem[0]) {
                if (Infinium.server != null) {
                    onDeath(player);
                }
            }
            return true;
        });
    }

    private static void playerConnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            if (SolarEclipseManager.isActive()) Infinium.adventure.audience(PlayerLookup.all(Infinium.server)).showBossBar(SolarEclipseManager.BOSS_BAR);
            EntityDataSaver dataPlayer = (EntityDataSaver) player;

            if (!SanityManager.totalPlayers.contains(player)) SanityManager.totalPlayers.add(player);

            if (dataPlayer.getPersistentData().get("infinium.cordura") == null) {
                dataPlayer.getPersistentData().putInt("infinium.cordura", 100);
            }


            return ActionResult.PASS;
        });

    }

    private static void onDeath(ServerPlayerEntity playerDied){
        BlockPos pos = playerDied.getBlockPos();
        Audience audience = Infinium.adventure.audience(PlayerLookup.all(Infinium.server));
        Title.Times times = Title.Times.times(Duration.ofSeconds(1), Duration.ofSeconds(6), Duration.ofSeconds(3));
        Title title = Title.title(Component.text(ChatFormatter.format("&6&k&l? &5Infinium &6&k&l?")), Component.text(""), times);

        ChatFormatter.broadcastMessage(ChatFormatter.formatWithPrefix("&7El jugador &6&l%player% &7sucumbio ante el\n&5&lVacío Infinito".replaceAll("%player%", playerDied.getEntityName())));
        playerDied.setHealth(20.0f);
        playerDied.changeGameMode(GameMode.SPECTATOR);
        if (pos.getY() < -64) {
            playerDied.teleport(pos.getX(), 1.5f, pos.getZ());
        }
        audience.showTitle(title);
        audience.playSound(Sound.sound(Key.key("infinium:player_death"), Sound.Source.PLAYER, 10, 0.7f));
        Infinium.executorService.schedule(() -> SolarEclipseManager.start(0.1), 13, TimeUnit.SECONDS);
    }



    private static void playerDisconnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            SanityManager.totalPlayers.remove(player);
            return ActionResult.PASS;
        });
    }
}