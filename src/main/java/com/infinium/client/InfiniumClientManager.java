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

public class InfiniumClientManager {

    private final InfiniumClient client;
    public KeyBinding checkTimeKey;

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
        ClientLifecycleEvents.CLIENT_STARTED.register(client1 -> {
            var client = MinecraftClient.getInstance();
            if (client == null) return;
            if (client.player == null) return;
            var player = client.player;
            for (BannedPlayers playerName : BannedPlayers.values()) {
                if (!player.getEntityName().equalsIgnoreCase(playerName.toString())) {
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
            if (checkTimeKey.wasPressed()) {
                ClientPlayNetworking.send(InfiniumPackets.TIME_CHECK_ID, PacketByteBufs.create());
            }
        });
    }

    public void registerKeys(){
        String KEY_CHECK_TIME = "key.infinium.check_time";
        String KEY_CHECK_TIME_CATEGORY = "key.category.infinium.infinium";
        checkTimeKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(KEY_CHECK_TIME, InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_H, KEY_CHECK_TIME_CATEGORY));
    }

    enum BannedPlayers {
        Mistaken_,
        AleIV,
        Carpincho02,
        CarpinchoLIVE,
    }

}
