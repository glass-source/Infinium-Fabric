package com.infinium.server.world.biomes;

import com.infinium.Infinium;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class InfiniumBiomes {

    public static RegistryKey<Biome> NIGHTMARE;
    public static RegistryKey<Biome> THE_VOID;

    public static void init(){
        NIGHTMARE = registerBiome("the_nightmare");
        THE_VOID = registerBiome("the_void");
    }

    private static RegistryKey<Biome> registerBiome(String name) {
        return RegistryKey.of(Registry.BIOME_KEY, new Identifier(Infinium.MOD_ID, name));
    }

}
