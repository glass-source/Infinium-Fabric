package com.infinium;

import com.infinium.items.ModItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Infinium implements ModInitializer {

    public static String MOD_ID = "infinium";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }

    @Override
    public void onInitialize() {
        ModItems.registerModItems();
    }
}
