package com.infinium.server.commands;

import com.infinium.api.eclipse.SolarEclipse;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.global.utils.DateUtils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

public class StaffCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("staff").requires(source -> source.hasPermissionLevel(4));

        literalArgumentBuilder
                .then(CommandManager.literal("days")
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("days", IntegerArgumentType.integer())
                                        .executes(context ->
                                                setDays(context, IntegerArgumentType.getInteger(context, "days")))))
                        .then(CommandManager.literal("get")
                                .executes(StaffCommand::getDay)))

                .then(CommandManager.literal("totems")
                        .then(CommandManager.literal("get")
                                .then(CommandManager.argument("player", EntityArgumentType.players())
                                        .executes(StaffCommand::getTotems)))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", EntityArgumentType.players())
                                        .then(CommandManager.argument("totems", IntegerArgumentType.integer())
                                                .executes(context ->
                                                        setTotems(context, EntityArgumentType.getPlayers(context, "player"),
                                                                IntegerArgumentType.getInteger(context, "totems")))
                                        ))))
                .then(CommandManager.literal("eclipse")
                        .then(CommandManager.literal("end")
                                .executes(StaffCommand::endEclipse))
                        .then(CommandManager.literal("get")
                                .executes(StaffCommand::getEclipseTime))
                        .then(CommandManager.literal("start")
                                .then(CommandManager.argument("duration", FloatArgumentType.floatArg())
                                        .executes(context ->
                                                startEclipse(context, FloatArgumentType.getFloat(context, "duration"))))));

        dispatcher.register(literalArgumentBuilder);
    }

    private static int setDays(CommandContext<ServerCommandSource> source, int newDay) {
        try {
            DateUtils.setDay(newDay);
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7El día se ha cambiado a: &6&l" + newDay), true);
            return 1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private static int getDay(CommandContext<ServerCommandSource> source) {
        try {
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7El día actual es: &6&l" + DateUtils.getDay()), false);
            return 1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private static int getTotems(CommandContext<ServerCommandSource> source) {
        try{
            var player = source.getSource().getPlayer();
            var data = ((EntityDataSaver) player).getPersistentData();
            int totems = data.getInt("infinium.totems");

            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7El jugador &6&l" + player.getEntityName() + " &7ha consumido &6&l" + totems + " &7Tótems de la inmortalidad"), false);
            return 1;
        }catch (CommandSyntaxException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static int setTotems(CommandContext<ServerCommandSource> source, Collection<ServerPlayerEntity> players, int totems) {
        try{
            players.forEach(player -> {
                var data = ((EntityDataSaver) player).getPersistentData();
                data.putInt("infinium.totems", totems);

                if (players.size() <= 1) {
                    source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Los Tótems del jugador &6&l" + player.getName().asString() + " &7han sido cambiados a &6&l" + totems), true);
                }
            });

            if (players.size() > 1) {
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Los Tótems de los jugadores seleccionados han sido cambiados a &6&l" + totems), true);
            }

        }catch (Exception ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! ese no es un numero valido!"));
        }
        return -1;
    }

    private static int endEclipse(CommandContext<ServerCommandSource> source) {
        SolarEclipse.end();
        return 0;
    }

    private static int startEclipse(CommandContext<ServerCommandSource> source, float duration) {
        try{
            SolarEclipse.start(duration);
            source.getSource().sendFeedback(ChatFormatter.text("&7Ha empezado un Eclipse Solar correctamente!"), true);
            return 1;
        }catch (Exception ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! ese no es un numero valido!"));
        }
        return -1;
    }

    private static int getEclipseTime(CommandContext<ServerCommandSource> source) {
        if (!SolarEclipse.isActive()) {
            source.getSource().sendFeedback(ChatFormatter.text("&7No hay un Eclipse Solar activo!"), false);
            return -1;
        }
        source.getSource().sendFeedback(ChatFormatter.text("&7Quedan " + SolarEclipse.getTimeToString() + " &7 de Solar Eclipse."), false);
        return 1;
    }


}
