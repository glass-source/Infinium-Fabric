package com.infinium.client;

import com.infinium.client.renderer.ModelPredicateProvider;
import com.infinium.client.renderer.game.hud.SanityHudOverlay;
import com.infinium.networking.InfiniumPackets;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

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


}
