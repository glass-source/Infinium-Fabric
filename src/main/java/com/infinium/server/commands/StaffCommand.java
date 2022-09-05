package com.infinium.server.commands;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.DateUtils;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.networking.InfiniumPackets;
import com.infinium.networking.packets.flashbang.FlashbangS2CPacket;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.sanity.SanityManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import java.util.Collection;

import static com.infinium.server.listeners.player.PlayerDeathListeners.firstTotemDebuff;
import static com.infinium.server.listeners.player.PlayerDeathListeners.secondTotemDebuff;

public class StaffCommand {

    private static final InfiniumServerManager core = Infinium.getInstance().getCore();
    private static final SolarEclipseManager eclipseManager = core.getEclipseManager();
    private static final SanityManager sanityManager = core.getSanityManager();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("staff").requires(source -> source.hasPermissionLevel(4));

        literalArgumentBuilder
                .then(CommandManager.literal("flashbang")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .executes(StaffCommand::showFlashbang)))
                .then(CommandManager.literal("cordura")
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("player", EntityArgumentType.players())
                                        .then(CommandManager.argument("newSanity", IntegerArgumentType.integer())
                                                .executes(context ->
                                                        changeSanity(context, EntityArgumentType.getPlayers(context, "player"),
                                                                IntegerArgumentType.getInteger(context, "newSanity"), false, true, false))
                                        )))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("player", EntityArgumentType.players())
                                        .then(CommandManager.argument("newSanity", IntegerArgumentType.integer())
                                                .executes(context ->
                                                        changeSanity(context, EntityArgumentType.getPlayers(context, "player"),
                                                                IntegerArgumentType.getInteger(context, "newSanity"), false, false, true))
                                        )))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", EntityArgumentType.players())
                                        .then(CommandManager.argument("newSanity", IntegerArgumentType.integer())
                                                .executes(context ->
                                                        changeSanity(context, EntityArgumentType.getPlayers(context, "player"),
                                                                IntegerArgumentType.getInteger(context, "newSanity"), true, false, false))
                                        ))))
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


    private static int showFlashbang(CommandContext<ServerCommandSource> source) {
        try{
            ServerPlayNetworking.send(source.getSource().getPlayer(), InfiniumPackets.FLASHBANG_SYNC_ID, new FlashbangS2CPacket(100, 25, 6, 0, 125, 0).write());
            return 1;
        }catch (Exception ex){
            return -1;
        }
    }

    private static int changeSanity(CommandContext<ServerCommandSource> source, Collection<ServerPlayerEntity> players, int number, boolean isSet, boolean isAdd, boolean isRemove) {
        try{
            var name = "";
            for (ServerPlayerEntity player : players) {
                name = player.getEntityName();
                if (isSet) sanityManager.set(player, number, sanityManager.SANITY);
                else if (isAdd) sanityManager.add(player, number, sanityManager.SANITY);
                else if (isRemove) sanityManager.decrease(player, number, sanityManager.SANITY);
            }


            if (players.size() <= 1) {
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7La cordura del jugador &6&l" + name + " &7ha sido cambiada"), true);
            }

            if (players.size() > 1) {
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7La cordura de todos los jugadores" + " ha sido cambiada"), true);
            }
            return 1;
        }catch (Exception ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! ese no es un número valido!"));
        }
        return -1;
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
        } catch (CommandSyntaxException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static int setTotems(CommandContext<ServerCommandSource> source, Collection<ServerPlayerEntity> players, int values) {
        try {
            players.forEach(player -> {
                var data = ((EntityDataSaver) player).getPersistentData();
                var totemString = "infinium.totems";
                data.putInt(totemString, values);
                var attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);

                if (values >= 30) {
                    if (attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.removeModifier(firstTotemDebuff);
                    if (!attributeInstance.hasModifier(secondTotemDebuff)) attributeInstance.addPersistentModifier(secondTotemDebuff);
                    player.setHealth(player.getMaxHealth());

                } else if (values >= 25) {
                    if (attributeInstance.hasModifier(secondTotemDebuff)) attributeInstance.removeModifier(secondTotemDebuff);
                    if (!attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.addPersistentModifier(firstTotemDebuff);

                } else {
                    attributeInstance.removeModifier(firstTotemDebuff);
                    attributeInstance.removeModifier(secondTotemDebuff);
                }

                player.setHealth(player.getMaxHealth());

                if (players.size() <= 1) {
                    source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Los Tótems del jugador &6&l" + player.getName().asString() + " &7han sido cambiados a &6&l" + values), true);
                }
            });

            if (players.size() > 1) {
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Los Tótems de los jugadores seleccionados han sido cambiados a &6&l" + values), true);
            }



            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! ese no es un número valido!"));
            return -1;
        }
    }

    private static int endEclipse(CommandContext<ServerCommandSource> source) {
        eclipseManager.end();
        return 0;
    }

    private static int startEclipse(CommandContext<ServerCommandSource> source, float duration) {
        try{
            eclipseManager.start(duration);
            source.getSource().sendFeedback(ChatFormatter.text("&7Ha empezado un Eclipse Solar correctamente!"), true);
            return 1;
        }catch (Exception ex) {
            ex.printStackTrace();
            source.getSource().sendError(ChatFormatter.text("&c¡Error! ese no es un número valido!"));
        }
        return -1;
    }

    private static int getEclipseTime(CommandContext<ServerCommandSource> source) {
        if (!eclipseManager.isActive()) {
            source.getSource().sendFeedback(ChatFormatter.text("&7No hay un Eclipse Solar activo!"), false);
            return -1;
        }

        source.getSource().sendFeedback(ChatFormatter.text("&7Quedan " + eclipseManager.getTimeToString() + " &7 de Solar Eclipse."), false);
        return 1;
    }


}
