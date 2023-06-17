package com.infinium.global.config.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.infinium.Infinium;
import net.minecraft.util.WorldSavePath;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
public class JsonConfig {
    private final Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private JsonObject jsonObject = new JsonObject();
    private final File file;
    public JsonConfig(String filename, String path) throws Exception {
        this.file = new File(path + File.separatorChar + filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            writeFile(file);
        } else {
            readFile(file);
        }
    }
    public static JsonConfig config(String filename) throws Exception {
        return new JsonConfig(filename, Infinium.getInstance().getCore().getServer().getSavePath(WorldSavePath.ROOT).toAbsolutePath().toString());
    }
    public void save() throws Exception {
        writeFile(file);
    }
    private void writeFile(File path) throws Exception {
        var writer = new FileWriter(path);
        gson.toJson(jsonObject, writer);
        writer.flush();
        writer.close();
    }
    private void readFile(File path) throws Exception {
        var reader = Files.newBufferedReader(Paths.get(path.getPath()));
        var object = gson.fromJson(reader, JsonObject.class);
        reader.close();
        jsonObject = object;
    }
    public JsonObject getJsonObject(){
        return this.jsonObject;
    }
    public void setJsonObject(JsonObject ob) {
        this.jsonObject = ob;
    }

}
