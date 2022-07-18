package com.infinium.global.sounds;

import com.infinium.Infinium;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumSounds {

    public static SoundEvent PLAYER_DEATH = registerSoundEvent("player_death");
    public static SoundEvent ECLIPSE_START = registerSoundEvent("eclipse_start");
    public static SoundEvent DUET = registerSoundEvent("duet_start");
    public static SoundEvent FALL_IN_LOVE = registerSoundEvent("fall_in_love");

    private static SoundEvent registerSoundEvent(String name){
        Identifier id = new Identifier(Infinium.MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
