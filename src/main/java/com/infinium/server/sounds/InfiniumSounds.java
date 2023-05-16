package com.infinium.server.sounds;

import com.infinium.Infinium;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumSounds {

    public static SoundEvent PLAYER_DEATH = registerSoundEvent("player_death");
    public static SoundEvent ECLIPSE_START = registerSoundEvent("eclipse_start");
    public static SoundEvent DUET = registerSoundEvent("duet_start");
    public static SoundEvent FALL_IN_LOVE = registerSoundEvent("fall_in_love");
    public static SoundEvent LOW_SANITY_1 = registerSoundEvent("low_sanity_1");
    public static SoundEvent LOW_SANITY_2 = registerSoundEvent("low_sanity_2");
    public static SoundEvent LOW_SANITY_3 = registerSoundEvent("low_sanity_3");
    public static SoundEvent LOW_SANITY_4 = registerSoundEvent("low_sanity_4");
    public static SoundEvent LOW_SANITY_5 = registerSoundEvent("low_sanity_5");
    public static SoundEvent LOW_SANITY_6 = registerSoundEvent("low_sanity_6");
    public static SoundEvent LOW_SANITY_7 = registerSoundEvent("low_sanity_7");
    public static SoundEvent LOW_SANITY_8 = registerSoundEvent("low_sanity_8");


    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(Infinium.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
