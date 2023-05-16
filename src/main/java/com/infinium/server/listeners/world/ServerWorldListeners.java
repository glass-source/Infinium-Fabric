package com.infinium.server.listeners.world;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;

import java.util.concurrent.Executors;

public class ServerWorldListeners {

    private final Infinium instance;
    public ServerWorldListeners(final Infinium instance) {
        this.instance = instance;
    }
    public void registerListeners() {
        chunkLoadCallback();
    }
    private void chunkLoadCallback() {
        //TODO fix crash
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {

            if (world.getRegistryKey().getValue().toString().equals("infinium:the_nightmare")) {
                var chunkPos = chunk.getPos();
                if (world.getRandom().nextInt(400) == 1) {
                    Executors.newSingleThreadExecutor().execute(() -> {

                        var blockpos = chunkPos.getCenterAtY(0);
                        var posX = blockpos.getX();
                        var posY = 65;
                        var posZ = blockpos.getZ();
                        Infinium.getInstance().LOGGER.info("Generated Zeppelin at: ${}, ${}, ${}", posX, posY, posZ);
                        instance.getCore().loadSchem("Nightmare_Bastion_Treasure", world, posX, posY, posZ);
                    });
                }
            }
        }));
    }

}
