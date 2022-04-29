package com.infinium.api.effects;

import com.infinium.Infinium;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class InfiniumEffects {

    public static StatusEffect IMMUNITY;
    public static StatusEffect MADNESS;

    private static StatusEffect register(String key, StatusEffect effect) {
        return Registry.register(Registry.STATUS_EFFECT, new Identifier(Infinium.MOD_ID, key), effect);
    }

    public static void init() {
        IMMUNITY = register("immunity", new ModStatusEffect(StatusEffectCategory.BENEFICIAL, 3381504));
        MADNESS = register("madness", new ModStatusEffect(StatusEffectCategory.HARMFUL, 12624973));
    }



}
