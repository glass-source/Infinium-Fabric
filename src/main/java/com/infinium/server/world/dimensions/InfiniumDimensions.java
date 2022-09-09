package com.infinium.server.world.dimensions;

import com.infinium.Infinium;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class InfiniumDimensions {
    public static RegistryKey<World> THE_NIGHTMARE;
    public static RegistryKey<DimensionType> THE_NIGHTMARE_TYPE;
    public static RegistryKey<World> THE_VOID;
    public static RegistryKey<DimensionType> THE_VOID_TYPE;
    public static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> THE_VOID_NOISE;

    public static void init() {

        THE_NIGHTMARE = RegistryKey.of(Registry.WORLD_KEY, new Identifier(Infinium.MOD_ID, "the_nightmare"));
        THE_NIGHTMARE_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, THE_NIGHTMARE.getValue());

        THE_VOID = RegistryKey.of(Registry.WORLD_KEY, new Identifier(Infinium.MOD_ID, "the_void"));
        THE_VOID_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, THE_VOID.getValue());
        THE_VOID_NOISE = RegistryKey.of(Registry.NOISE_WORLDGEN, THE_VOID.getValue());
    }



}
