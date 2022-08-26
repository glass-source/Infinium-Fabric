package com.infinium.server.items.custom.misc;

import com.infinium.server.items.custom.InfiniumItem;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.sound.SoundEvent;

public class InfiniumDiscItem extends MusicDiscItem implements InfiniumItem {

    public InfiniumDiscItem(int comparatorOutput, SoundEvent sound, Settings settings) {
        super(comparatorOutput, sound, settings);
    }
}
