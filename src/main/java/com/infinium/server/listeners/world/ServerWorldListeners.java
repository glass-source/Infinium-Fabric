package com.infinium.server.listeners.world;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerWorldListeners {

    private final Infinium instance;
    public ServerWorldListeners(final Infinium instance) {
        this.instance = instance;
    }
    public void registerListeners() {
        chunkLoadCallback();
    }
    private void chunkLoadCallback() {
        AtomicInteger counter = new AtomicInteger();
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {

            if (chunk.isEmpty())

            switch (world.getRegistryKey().getValue().toString()) {
                case "infinium:the_nightmare" -> {



                    if (world.getRandom().nextInt(250 + counter.get()) == 1) {
                        counter.getAndAdd(25);
                        Infinium.getInstance().getExecutor().schedule(() -> {
                            var chunkPos = chunk.getPos();
                            var blockpos = chunkPos.getCenterAtY(0);

                            var posX = blockpos.getX();
                            var posY = 130;
                            var posZ = blockpos.getZ();
                            chunk.isEmpty();
                            Infinium.getInstance().LOGGER.info("Generated Zeppelin at: ${}, ${}, ${}", posX ,posY, posZ);
                            instance.getCore().loadSchem("ZepelinDia0", world, posX, posY, posZ);
                        }, 750, TimeUnit.MILLISECONDS);
                    }
                }

                case "infinium:the_void" -> {

                }

                case "minecraft:overworld" -> {

                }

            }




        }));
    }



}
