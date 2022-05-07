package com.infinium.global.commands;

import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.api.utils.ChatFormatter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.infinium.api.utils.ChatFormatter.cd;

public class SolarEclipseCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var eclipse = cd("eclipse");

        dispatcher.register(eclipse.then(cd("end").executes(SolarEclipseCommand::end).requires(source -> source.hasPermissionLevel(2))));
        dispatcher.register(eclipse.then(cd("start").executes(source -> start(source, 0.25f)).requires(source -> source.hasPermissionLevel(2))));
        dispatcher.register(eclipse.then(cd("get").executes(SolarEclipseCommand::get).requires(source -> source.hasPermissionLevel(2))));


    }

    private static int end(CommandContext<ServerCommandSource> source) {
        SolarEclipseManager.end();
        return 0;
    }

    private static int start(CommandContext<ServerCommandSource> source, float duration) {
        SolarEclipseManager.start(duration);
        return Float.floatToIntBits(duration);
    }

    private static int get(CommandContext<ServerCommandSource> source) {
        //source.getSource().sendFeedback(Text.of(String.valueOf(SolarEclipseManager.getTimeToEnd())), true);
        source.getSource().sendFeedback(ChatFormatter.text("&7Quedan " +SolarEclipseManager.getTime() + " &7 de Solar Eclipse."), false);
        return (int) SolarEclipseManager.getTimeToEnd();
    }




}
