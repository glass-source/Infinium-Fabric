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

        THE_NIGHTMARE = registerWorld("the_nightmare");
        THE_VOID = registerWorld("the_void");

        THE_NIGHTMARE_TYPE = registerDimension(THE_NIGHTMARE.getValue());
        THE_VOID_TYPE = registerDimension(THE_VOID.getValue());

        THE_VOID_NOISE = registerNoise(THE_VOID.getValue());
    }

    private static RegistryKey<World> registerWorld(String id) {
        return RegistryKey.of(Registry.WORLD_KEY, new Identifier(Infinium.MOD_ID, id));
    }

    private static RegistryKey<DimensionType> registerDimension(Identifier id) {
        return RegistryKey.of(Registry.DIMENSION_TYPE_KEY, id);
    }

    private static RegistryKey<DoublePerlinNoiseSampler.NoiseParameters> registerNoise(Identifier id){
        return RegistryKey.of(Registry.NOISE_WORLDGEN, id);
    }

}
