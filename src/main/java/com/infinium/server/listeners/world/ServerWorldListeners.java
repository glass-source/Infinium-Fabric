package com.infinium.server.listeners.world;

import com.infinium.Infinium;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.List;

public class ServerWorldListeners {

    private final Infinium instance;
    public ServerWorldListeners(final Infinium instance) {
        this.instance = instance;
    }
    public void registerListeners() {
        //chunkLoadCallback();
    }
    private final List<Chunk> loadedChunks = new ArrayList<>();

    private Runnable generateStructureTask;
    private void chunkLoadCallback() {
        //TODO fix crash
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {

            if (loadedChunks.contains(chunk)) return;
            if (world.getRandom().nextInt(125) == 1) {
                generateStructureTask = () -> {
                    loadedChunks.add(chunk);
                    switch (world.getRegistryKey().getValue().toString()) {
                        case "infinium:the_nightmare" -> {
                            var chunkPos = chunk.getPos();
                            var blockpos = chunkPos.getCenterAtY(0);
                            var posX = blockpos.getX();
                            var posY = 130;
                            var posZ = blockpos.getZ();
                            Infinium.getInstance().LOGGER.info("Generated Zeppelin at: ${}, ${}, ${}", posX ,posY, posZ);
                            instance.getCore().loadSchem("ZepelinDia0", world, posX, posY, posZ);
                        }

                        case "infinium:the_void" -> {

                        }

                        case "minecraft:overworld" -> {

                        }

                    }
                };
                world.getServer().execute(generateStructureTask);

            }
        }));
    }

    private void chunkUnloadCallback() {
        ServerChunkEvents.CHUNK_UNLOAD.register((((world, chunk) -> {

        })));
    }



}
