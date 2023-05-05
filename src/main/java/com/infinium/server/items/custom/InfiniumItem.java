package com.infinium.server.items.custom;

import com.infinium.global.utils.EntityDataSaver;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

import java.util.Objects;

public interface InfiniumItem {
    default void setCooldown(PlayerEntity user, Item item, int durationTicks) {
        var data = ((EntityDataSaver) user).getPersistentData();
        var cooldownManager = user.getItemCooldownManager();
        var endingTickString = "infinium.cooldown." + item + ".ending-tick";
        var startingTick = Objects.requireNonNull(user.getServer()).getTicks();
        var endingTick = startingTick + durationTicks;
        data.putInt(endingTickString, endingTick);
        cooldownManager.set(item, durationTicks);
    }

}
