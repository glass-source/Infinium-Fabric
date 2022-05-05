package com.infinium.api.commands;

import com.infinium.api.events.eclipse.SolarEclipseManager;
import com.infinium.api.utils.ChatFormatter;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static com.infinium.api.utils.ChatFormatter.CD;

public class SolarEclipseCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var eclipse = CD("eclipse");

        dispatcher.register(eclipse.then(CD("end").executes(SolarEclipseCommand::end).requires(source -> source.hasPermissionLevel(2))));
        dispatcher.register(eclipse.then(CD("start").executes(source -> start(source, 0.25f)).requires(source -> source.hasPermissionLevel(2))));
        dispatcher.register(eclipse.then(CD("get").executes(SolarEclipseCommand::get).requires(source -> source.hasPermissionLevel(2))));

        /*
        dispatcher.register( (((eclipse.requires((source) -> {

            return source.hasPermissionLevel(2);
        })).then(((LiteralArgumentBuilder)CommandManager.literal("start").executes((context) -> {
            return start(context, 0.5f);

        })).then(CommandManager.argument("duration", FloatArgumentType.floatArg(0, 1000000)).executes((context) -> {
            return start(context, FloatArgumentType.getFloat(context, "duration"));

        })))).then((CD("get").executes((context) -> {
            return get(context);
        })))).then((CD("end").executes((context) -> {
            return end(context);
        }))));
         */

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
        source.getSource().sendFeedback(Text.of(String.valueOf(SolarEclipseManager.getTimeToEnd())), true);
        return (int) SolarEclipseManager.getTimeToEnd();
    }




}
