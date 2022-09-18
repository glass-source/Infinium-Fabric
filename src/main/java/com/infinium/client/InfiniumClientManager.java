package com.infinium.client;

import com.infinium.client.renderer.ModelPredicateProvider;
import com.infinium.client.renderer.game.hud.SanityHudOverlay;
import com.infinium.networking.InfiniumPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.UUID;

public class InfiniumClientManager {
    private final InfiniumClient client;
    public KeyBinding checkTimeKey;
    private int packetCooldown = 100;

    public InfiniumClientManager(InfiniumClient client) {
        this.client = client;
    }

    public void onClientStart(){
        registerItemModels();
        registerEntityRenderers();
        registerKeys();
        registerHudElements();
        checkKeyInput();
        registerPackets();
        checkBannedPlayers();
       // registerShaderCallback();
    }

    private void registerPackets(){
        InfiniumPackets.initS2CPackets();
    }
    private void checkBannedPlayers() {
        ClientTickEvents.START_CLIENT_TICK.register(client1 -> {
            var client = MinecraftClient.getInstance();
            if (client == null) return;
            if (client.player == null) return;
            var player = client.player;
            for (BannedPlayers playerName : BannedPlayers.values()) {
                if (player.getUuid().equals(playerName.uuid)) {
                    try {
                        client.getResourceManager().getAllResources(new Identifier("minecraft:")).clear();
                        client.getWindow().close();
                    } catch (IOException ignored) {}
                }
            }
        });
    }
    private void registerHudElements(){
        HudRenderCallback.EVENT.register(new SanityHudOverlay());
    }

    private void registerItemModels(){
        ModelPredicateProvider.initItemModels();
    }

    private void registerEntityRenderers(){
        ModelPredicateProvider.registerEntityRenderer();
        ModelPredicateProvider.registerEntityModelLayers();
    }

    public void checkKeyInput(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (packetCooldown >= 0) packetCooldown--;
            if (checkTimeKey.wasPressed()) {
                if (packetCooldown <= 0) {
                    ClientPlayNetworking.send(InfiniumPackets.TIME_CHECK_ID, PacketByteBufs.create());
                    packetCooldown = 100;
                }
            }
        });
    }

    public void registerKeys(){
        String KEY_CHECK_TIME = "key.infinium.check_time";
        String KEY_CHECK_TIME_CATEGORY = "key.category.infinium.infinium";
        checkTimeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_CHECK_TIME, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CHECK_TIME_CATEGORY));
    }

    enum BannedPlayers {
        Mistaken_(UUID.randomUUID()),
        AleIV(UUID.randomUUID()),
        Carpincho02("4881b948-f979-4b46-9031-ceb5f02e15d5"),
        mrswz(UUID.fromString("381ce4f8-774c-4842-98e1-027c9ae9e8c5")),
        Litro6666(UUID.fromString("d5216e9e-3959-4466-aa67-66efaa583abd")),
        CarpinchoLIVE("fb02dc04-e13e-46ad-9404-bdbecf1d35b5");

        private final UUID uuid;
        BannedPlayers(UUID playerUuid){
            this.uuid = playerUuid;
        }
        BannedPlayers(String uuid) {
            this.uuid = UUID.fromString(uuid);
        }

    }

}
