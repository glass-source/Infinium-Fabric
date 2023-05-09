package com.infinium.server.functions;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/*
 * This is the latest source code of Collective.
 * Minecraft version: 1.18.2.
 *
 * Please don't distribute without permission.
 * For all Minecraft modding projects, feel free to visit my profile page on CurseForge or Modrinth.
 *  CurseForge: https://curseforge.com/members/serilum/projects
 *  Modrinth: https://modrinth.com/user/serilum
 *  Overview: https://serilum.com/
 *
 * If you are feeling generous and would like to support the development of the mods, you can!
 *  https://ricksouth.com/donate contains all the information. <3
 *
 * Thanks for looking at the source code! Hope it's of some use to your project. Happy modding!
 */

public class HeadFunctions {

    public static String playerdataurl = "https://api.mojang.com/users/profiles/minecraft/";
    public static String skindataurl = "https://sessionserver.mojang.com/session/minecraft/profile/";

    public static ItemStack getPlayerHead(String playername, Integer amount) {
        String nameIdJsonString = readStringFromURL(playerdataurl + playername.toLowerCase());
        if (nameIdJsonString.equals("")) {
            return null;
        }

        try {
            Map<String, String> nameIdJson = new Gson().fromJson(nameIdJsonString, new TypeToken<HashMap<String, String>>() {}.getType());

            String headName = nameIdJson.get("name");
            String headUUID = nameIdJson.get("id");

            String profileJsonString = readStringFromURL(skindataurl + headUUID);
            if (profileJsonString.equals("")) {
                return null;
            }

            String[] rawValue = profileJsonString.replaceAll(" ", "").split("value\":\"");

            String texturevalue = rawValue[1].split("\"")[0];
            String d = new String(Base64.getDecoder().decode((texturevalue.getBytes())));

            String texture = Base64.getEncoder().encodeToString((("{\"textures\"" + d.split("\"textures\"")[1]).getBytes()));
            String oldid = new UUID(texture.hashCode(), texture.hashCode()).toString();

            return HeadFunctions.getTexturedHead(headName + "'s Head", texture, oldid, amount);
        }
        catch (ArrayIndexOutOfBoundsException ignored) { }

        return null;
    }

    private static ItemStack getTexturedHead(String headname, String texture, String oldid, Integer amount) {
        ItemStack texturedhead = new ItemStack(Items.PLAYER_HEAD, amount);

        List<Integer> intarray = UUIDFunctions.oldIdToIntArray(oldid);

        NbtCompound skullOwner = new NbtCompound();
        skullOwner.putIntArray("Id", intarray);

        NbtCompound properties = new NbtCompound();
        NbtList textures = new NbtList();
        NbtCompound tex = new NbtCompound();
        tex.putString("Value", texture);
        textures.add(tex);
        properties.put("textures", textures);
        skullOwner.put("Properties", properties);
        texturedhead.setSubNbt("SkullOwner", skullOwner);
        texturedhead.setCustomName(Text.of(headname));

        return texturedhead;
    }

    public static boolean hasStandardHead(String mobname) {
        return mobname.equals("creeper") || mobname.equals("zombie") || mobname.equals("skeleton");
    }

    public static String readStringFromURL(String requestURL) {
        String data = "";
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\A");
            data = scanner.hasNext() ? scanner.next() : "";
        }
        catch(Exception ignored) {}

        return data;
    }

}
