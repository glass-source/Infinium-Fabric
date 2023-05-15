package com.infinium.server.listeners.world;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.util.math.Box;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerWorldListeners {

    private final Infinium instance;
    public ServerWorldListeners(final Infinium instance) {
        this.instance = instance;
    }
    public void registerListeners() {
        //chunkLoadCallback();
    }
    private final Box chunkRadius = new Box(250.0f, -64.0f, 250.0f, -250.0f, 320.0f, -250.0f);
    private void chunkLoadCallback() {
        //TODO fix crash
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {

            if (world.getRegistryKey().getValue().toString().equals("infinium:the_nightmare")) {
                var chunkPos = chunk.getPos();
                if (world.getRandom().nextInt(220) == 1) {
                    Executors.newSingleThreadScheduledExecutor().schedule(() -> {
                        if (chunkRadius.contains(chunkPos.getCenterX(), 0, chunkPos.getCenterZ())) return;
                        chunkRadius.expand(chunkPos.getCenterX(), 0, chunkPos.getCenterZ());
                        var blockpos = chunkPos.getCenterAtY(0);
                        var posX = blockpos.getX();
                        var posY = 130;
                        var posZ = blockpos.getZ();
                        Infinium.getInstance().LOGGER.info("Generated Zeppelin at: ${}, ${}, ${}", posX, posY, posZ);
                        instance.getCore().loadSchem("ZepelinDia0", world, posX, posY, posZ);
                    }, 4, TimeUnit.SECONDS);
                }
            }
        }));
    }

}
