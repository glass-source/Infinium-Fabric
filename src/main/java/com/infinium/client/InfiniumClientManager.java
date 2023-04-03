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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.glfw.GLFW;

import java.io.IOException;
import java.util.UUID;

public class InfiniumClientManager {
    public KeyBinding checkTimeKey;
    private int packetCooldown = 100;
    private final ModelPredicateProvider modelPredicateProvider;

    public InfiniumClientManager() {
        this.modelPredicateProvider = new ModelPredicateProvider();
    }

    public void onClientStart(){
        registerKeys();
        registerHudElements();
        checkKeyInput();
        registerPackets();
        modelPredicateProvider.init();
    }


    private void registerPackets(){
        InfiniumPackets.initS2CPackets();
    }
    private void registerHudElements(){
        HudRenderCallback.EVENT.register(new SanityHudOverlay());
    }

    public void checkKeyInput(){
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (packetCooldown >= 0) packetCooldown--;
            if (!checkTimeKey.wasPressed()) return;

            if (packetCooldown <= 0) {
                ClientPlayNetworking.send(InfiniumPackets.TIME_CHECK_ID, PacketByteBufs.create());
                packetCooldown = 100;
            }
        });
    }

    public void registerKeys(){
        String KEY_CHECK_TIME = "key.infinium.check_time";
        String KEY_CHECK_TIME_CATEGORY = "key.category.infinium.infinium";
        checkTimeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_CHECK_TIME, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CHECK_TIME_CATEGORY));
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
    private enum BannedPlayers {
        DREAM("ec70bcaf-702f-4bb8-b48d-276fa52a780c"),
        ALEIV("f04891b6-3a22-49c3-a8c0-bdf7e415243a"),
        DjMaRiiO("b8351a40-f0dc-4996-adfd-101311b8fdd9"),
        TETUISMC("9c626690-39a8-4163-ae70-8643caa6009c");


        private final UUID uuid;
        BannedPlayers(UUID playerUuid){
            this.uuid = playerUuid;
        }
        BannedPlayers(String playerUuid) {
            this.uuid = UUID.fromString(playerUuid);
        }
    }

}
