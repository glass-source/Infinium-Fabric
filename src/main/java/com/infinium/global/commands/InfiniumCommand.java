package com.infinium.global.commands;

import com.infinium.api.config.InfiniumConfig;
import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.utils.EntityDataSaver;
import com.infinium.api.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

import java.time.format.DateTimeParseException;

import static com.infinium.api.utils.ChatFormatter.cd;

public class InfiniumCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var infinium = cd("infinium");
        dispatcher.register(infinium.then(cd("totems").executes(InfiniumCommand::totems)));
        dispatcher.register(infinium.then(cd("days").executes(InfiniumCommand::days)));
    }

    private static int totems(CommandContext<ServerCommandSource> source) {
        try {
            var data = ((EntityDataSaver) source.getSource().getPlayer()).getPersistentData();
            int totems = data.getInt("infinium.totems");
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Has consumido &6&l" + totems + " &7Totems de la inmortalidad"), false);
        } catch (CommandSyntaxException ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! notificale esto a algun developer..."));
        }
        return 0;
    }

    private static int days(CommandContext<ServerCommandSource> source) {
        try{
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Estamos en el día &6&l" + Utils.getDay()), false);
        }catch (ExceptionInInitializerError | DateTimeParseException ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! notificale esto a algun developer..."));
        }
        return 0;
    }

}
