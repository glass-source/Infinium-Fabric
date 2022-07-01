package com.infinium.global.world.dimensions;

import com.infinium.Infinium;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class InfiniumDimensions {

    public static final RegistryKey<World> TEST_DIMENSION_KEY = RegistryKey.of(Registry.WORLD_KEY,
            new Identifier(Infinium.MOD_ID, "test"));
    public static final RegistryKey<DimensionType> TEST_TYPE_KEY = RegistryKey.of(Registry.DIMENSION_TYPE_KEY,
            TEST_DIMENSION_KEY.getValue());


    public static void register() {
        Infinium.LOGGER.debug("Registering ModDimensions for " + Infinium.MOD_ID);
    }

}
