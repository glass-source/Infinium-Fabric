package com.infinium.global.config.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.infinium.Infinium;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * A class designed to savePlayerData any state from objects to json files.
 * @author Jcedeno.
 * @author zLofro
 * //Gracias cdeno mi dios carreaste este proyecto con esta util y no lo sabes.
 */
public class JsonConfig {
    private final Gson gson = new GsonBuilder()
            .serializeNulls().setPrettyPrinting()
            .create();

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

    /**
     * A static constructor that creates a new {@link JsonConfig} object in the
     * specified serverWorld's folder.
     *
     * @param filename The name of the file to create.
     * @return A new {@link JsonConfig} object.
     * @throws Exception If the file cannot be created.
     */
    public static JsonConfig config(String filename) throws Exception {
        return new JsonConfig(filename, Infinium.getInstance().getCore().getServer().getFile("world").getAbsolutePath());
    }
    public static JsonConfig userData(String filename) throws Exception {
        return new JsonConfig(filename, Infinium.getInstance().getCore().getServer().getFile("world").getAbsolutePath() + File.separatorChar + "user");
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
