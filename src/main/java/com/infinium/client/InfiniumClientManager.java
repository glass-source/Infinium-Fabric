package com.infinium.client;

import com.infinium.Infinium;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.UUID;

public class InfiniumClientManager {
    private final InfiniumClient client;
    public KeyBinding checkTimeKey;
    private int packetCooldown = 100;
    private static InfiniumClientManager cManager;

    public InfiniumClientManager(InfiniumClient client) {
        cManager = this;
        this.client = client;
    }

    public void onClientStart(){
        registerItemModels();
        registerEntityRenderers();
        registerKeys();
        registerHudElements();
        checkKeyInput();
        registerPackets();
    }

    private void registerPackets(){
        InfiniumPackets.initS2CPackets();
    }
    public void checkBannedPlayers(PlayerEntity player) {
        var logger = Infinium.getInstance().LOGGER;
        logger.info("Checking banned players...");
        for (BannedPlayers playerName : BannedPlayers.values()) {
            if (player.getUuid().equals(playerName.uuid)) {
                var client = MinecraftClient.getInstance();
                logger.info("You are banned from using this mod! \nClosing the game now.");
                client.execute(() -> client.getWindow().close());
                break;
            }
        }
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

    public static InfiniumClientManager getInstance() {
        return cManager;
    }

    enum BannedPlayers {
        CHECK("2");
        private final UUID uuid;
        BannedPlayers(UUID playerUuid){
            this.uuid = playerUuid;
        }
        BannedPlayers(String playerUuid) {
            this.uuid = UUID.fromString(playerUuid);
        }
    }

}
