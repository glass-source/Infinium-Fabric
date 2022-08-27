package com.infinium.client;

import com.infinium.Infinium;
import com.infinium.client.renderer.ModelPredicateProvider;
import com.infinium.client.renderer.player.hud.SanityHudOverlay;
import com.infinium.networking.InfiniumPackets;
import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

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
       // registerShaderCallback();
    }

    private void registerPackets(){
        InfiniumPackets.registerS2CPackets();
    }

    private void registerShaderCallback(){
        ManagedShaderEffect BLOODMOON_SHADER = ShaderEffectManager.getInstance().manage(new Identifier("infinium", "shaders/post/bloodmoon.json"));

        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (Infinium.getInstance().getCore().getEclipseManager().isActive()) {
               BLOODMOON_SHADER.render(tickDelta);
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

}
