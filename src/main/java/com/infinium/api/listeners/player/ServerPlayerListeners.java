package com.infinium.api.listeners.player;

import com.infinium.Infinium;
import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.api.events.players.ServerPlayerConnectionEvents;
import com.infinium.api.items.global.InfiniumItems;
import com.infinium.api.utils.ChatFormatter;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

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

            player.getItemsHand().forEach(itemStack -> {
                var item = itemStack.getItem();

                if (item.equals(Items.TOTEM_OF_UNDYING) && damageSource.isOutOfWorld()) {
                    hasTotem[0] = false;
                    return;
                }


                if (item.equals(Items.TOTEM_OF_UNDYING) || item.equals(InfiniumItems.VOID_TOTEM)) {
                    hasTotem[0] = true;
                }
            });

            if (!hasTotem[0]) {
                if (Infinium.server != null) {
                    SolarEclipseManager.start(0.25);
                    Title title = Title.title(Component.text(ChatFormatter.format("&6&k&l? &5Infinium &6&k&l?")), Component.text("h"));
                    PlayerLookup.all(Infinium.server).forEach(serverPlayerEntity -> {
                        Infinium.adventure.audience(PlayerLookup.all(Infinium.server)).showTitle(title);
                        serverPlayerEntity.sendMessage(Text.of(ChatFormatter.formatWithPrefix("&7El jugador: &c&l%player% &7ha muerto".replaceAll("%player%", player.getEntityName()))), false);
                    });
                }
            }

            return true;
        });
    }

    private static void playerConnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            player.sendMessage(Text.of("Conectado!"), false);
            return ActionResult.PASS;
        });
    }

    private static void playerDisconnectCallback(){
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            player.sendMessage(Text.of("Desconectado!"), false);
            return ActionResult.PASS;
        });
    }

}
