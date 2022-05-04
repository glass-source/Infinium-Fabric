package com.infinium.api.commands;

import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.api.utils.ChatFormatter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.infinium.api.utils.ChatFormatter.CD;

public class SolarEclipseCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var eclipse = CD("eclipse");
        dispatcher.register(eclipse.then(CD("end").executes(SolarEclipseCommand::end)));
        dispatcher.register(eclipse.then(CD("start").executes(SolarEclipseCommand::start)));
        dispatcher.register(eclipse.then(CD("get").executes(SolarEclipseCommand::get)));
    }

    private static int end(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        SolarEclipseManager.end();
        return 0;
    }

    private static int start(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        SolarEclipseManager.start(0.1);
        return 0;
    }

    private static int get(CommandContext<ServerCommandSource> serverCommandSourceCommandContext) {
        serverCommandSourceCommandContext.getSource().sendFeedback(Text.of(String.valueOf(SolarEclipseManager.getTimeToEnd())), true);
        return 0;
    }




}
