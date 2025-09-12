package com.infinium.server.commands;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.server.command.ServerCommandSource;

import java.time.format.DateTimeParseException;

import static com.infinium.global.utils.ChatFormatter.cd;

public class InfiniumCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var infinium = cd("infinium");
        dispatcher.register(infinium.then(cd("totems").executes(InfiniumCommand::totems)));
        dispatcher.register(infinium.then(cd("days").executes(InfiniumCommand::days)));
    }

    private static int totems(CommandContext<ServerCommandSource> source) {
        try {
            var data = ((EntityDataSaver) source.getSource().getPlayer()).infinium_Fabric$getPersistentData();
            int totems = data.getInt("infinium.totems");
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Has consumido &6&l" + totems + " &7Tótems de la inmortalidad"), false);
            return 1;
        } catch (CommandSyntaxException ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! notificale esto a @Asunder..."));
            return -1;
        }
    }

    private static int days(CommandContext<ServerCommandSource> source) {
        try{
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Estamos en el día &6&l" + Infinium.getInstance().getDateUtils().getCurrentDay()), false);
            return 1;
        }catch (ExceptionInInitializerError | DateTimeParseException ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! notificale esto a @Asunder..."));
            return -1;
        }

    }
}
