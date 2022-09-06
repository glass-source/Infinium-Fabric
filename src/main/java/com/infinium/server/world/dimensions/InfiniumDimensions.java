package com.infinium.server.world.dimensions;

import com.infinium.Infinium;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;

public class InfiniumDimensions {

    public static final RegistryKey<World> THE_NIGHTMARE = RegistryKey.of(Registry.WORLD_KEY, new Identifier(Infinium.MOD_ID, "the_nightmare"));
    public static final RegistryKey<DimensionType> THE_NIGHTMARE_TYPE = RegistryKey.of(Registry.DIMENSION_TYPE_KEY, THE_NIGHTMARE.getValue());
    public static RegistryKey<Biome> NIGHTMARE;
    public static void init() {
        NIGHTMARE = registerBiome("the_nightmare");
    }

    private static RegistryKey<Biome> registerBiome(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Infinium.MOD_ID, name));
    }

}
