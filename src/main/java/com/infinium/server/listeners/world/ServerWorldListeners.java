package com.infinium.server.listeners.world;

import com.infinium.Infinium;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.fabric.FabricAdapter;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents;
import net.minecraft.world.World;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ServerWorldListeners {

    public void registerListeners() {
        chunkLoadCallback();
    }
    private void chunkLoadCallback() {
        ServerChunkEvents.CHUNK_LOAD.register(((world, chunk) -> {
            switch (world.getRegistryKey().getValue().toString()) {
                case "infinium:the_nightmare" -> {

                    if (world.getRandom().nextInt(50) == 1) {
                        Infinium.getInstance().getExecutor().schedule(() -> {
                            var chunkPos = chunk.getPos();
                            var blockpos = chunkPos.getCenterAtY(0);

                            var posX = blockpos.getX();
                            var posY = 130;
                            var posZ = blockpos.getZ();
                            chunk.isEmpty();
                            Infinium.getInstance().LOGGER.info("Generated Zeppelin at: ${}, ${}, ${}", posX ,posY, posZ);
                            loadSchem("ZepelinDia14", world, posX, posY, posZ);
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

    public static void loadSchem(String filename, World world, int X, int Y, int Z){
        File file = new File(Infinium.getInstance().getCore().getServer().getFile("world").getAbsolutePath() + "/schematics/" + filename + ".schem");
        com.sk89q.worldedit.world.World adaptedWorld = FabricAdapter.adapt(world);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        try {
            assert format != null;
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();

                try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(adaptedWorld,-1)) {
                    Operation operation = new ClipboardHolder(clipboard).createPaste(editSession).to(BlockVector3.at(X, Y, Z)).copyEntities(true)
                            .ignoreAirBlocks(true).build();
                    try {
                        Operations.complete(operation);
                        editSession.close();

                    } catch (WorldEditException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
