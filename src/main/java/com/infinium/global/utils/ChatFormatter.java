package com.infinium.global.utils;

import com.infinium.Infinium;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.kyori.adventure.text.Component;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.Validate;

public class ChatFormatter {

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static String format(String text) {return translateAlternateColorCodes('&', text);}

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */

    public static Text text(String text) {
        return Text.of(ChatFormatter.format(text));
    }
    public static Text textWithPrefix(String text) {
        return Text.of(ChatFormatter.format(prefix + text));
    }

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static String formatWithPrefix(String text) {return prefix + format(text);}
    public static LiteralArgumentBuilder<ServerCommandSource> cd(String value) {
        return CommandManager.literal(value);
    }

    /**
     * Translates a string using an alternate color code character into a
     * string that uses the internal Formatting.FORMATTING_CODE_PREFIX color code
     * character. The alternate color code character will only be replaced if
     * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * @param altColorChar The alternate color code character to replace. Ex: {@literal &}
     * @param text Text containing the alternate color code character.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character.
     */
    public static String translateAlternateColorCodes(char altColorChar, String text) {
        Validate.notNull(text, "Cannot translate null text.");

        char[] b = text.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = Formatting.FORMATTING_CODE_PREFIX;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    public static Component stringToComponent(String s){
        return Component.text(format(s));
    }
    public static String prefix = format("&5&lInfinium&6&lSMP &8>> &r");
    public static void broadcastMessage(String message) {
        var core = Infinium.getInstance().getCore();
        core.getAdventure().audience(PlayerLookup.all(core.getServer())).sendMessage(Component.text(format(message)));
    }
    public static void broadcastMessageWithPrefix(String message) {
        broadcastMessage(prefix + message);
    }
}
