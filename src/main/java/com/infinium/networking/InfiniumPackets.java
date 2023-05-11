package com.infinium.networking;

import com.infinium.Infinium;
import com.infinium.networking.packets.sanity.KairosCheckC2SPacket;
import com.infinium.networking.packets.sanity.SanitySyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class InfiniumPackets {
    public static final Identifier SANITY_SYNC_ID = Infinium.id("sanity_sync");
    public static final Identifier TIME_CHECK_ID = Infinium.id("time_check");
    public static void initC2SPackets(){
        ServerPlayNetworking.registerGlobalReceiver(TIME_CHECK_ID, KairosCheckC2SPacket::receive);
    }

    public static void initS2CPackets(){
        ClientPlayNetworking.registerGlobalReceiver(SANITY_SYNC_ID, SanitySyncS2CPacket::receive);
    }

}
