package com.infinium.server.commands;

import com.infinium.Infinium;
import com.infinium.global.utils.ChatFormatter;
import com.infinium.global.utils.EntityDataSaver;
import com.infinium.server.InfiniumServerManager;
import com.infinium.server.eclipse.SolarEclipseManager;
import com.infinium.server.sanity.SanityManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Collection;

import static com.infinium.server.listeners.player.PlayerDeathListeners.*;

public class StaffCommand {

    private static final InfiniumServerManager core = Infinium.getInstance().getCore();
    private static final SolarEclipseManager eclipseManager = core.getEclipseManager();
    private static final SanityManager sanityManager = core.getSanityManager();

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("staff").requires(source -> source.hasPermissionLevel(4));

        literalArgumentBuilder

                .then(CommandManager.literal("loadstructure")
                        .then(CommandManager.argument("filename", StringArgumentType.string())
                                .executes(context ->
                                        loadStructure(context, StringArgumentType.getString(context, "filename"))
                                 )))

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
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context ->
                                                getTotems(context, EntityArgumentType.getPlayer(context, "player")))))

                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .then(CommandManager.argument("totems", IntegerArgumentType.integer())
                                                .executes(context ->
                                                        setTotems(context, EntityArgumentType.getPlayer(context, "player"),
                                                                IntegerArgumentType.getInteger(context, "totems")))
                                        ))))


                .then(CommandManager.literal("openinv")
                        .then(CommandManager.literal("enderchest")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context ->
                                                openEnderchest(context, EntityArgumentType.getPlayer(context, "player")))))

                        .then(CommandManager.literal("inventory")
                                .then(CommandManager.argument("player", EntityArgumentType.player())
                                        .executes(context ->
                                                openInventory(context, EntityArgumentType.getPlayer(context, "player")))))


                )


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

    private static int openInventory(CommandContext<ServerCommandSource> source, ServerPlayerEntity player2) {
        try {

            source.getSource().getPlayer().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player)
                    -> createGeneric9x4(syncId, inventory, player2.getInventory()), ChatFormatter.text("Inventory of " + player2.getEntityName())));

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    public static GenericContainerScreenHandler createGeneric9x4(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        return new GenericContainerScreenHandler(ScreenHandlerType.GENERIC_9X4, syncId, playerInventory, inventory, 4);
    }
    private static int openEnderchest(CommandContext<ServerCommandSource> source, ServerPlayerEntity player2) {
        try {

            source.getSource().getPlayer().openHandledScreen(new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                    GenericContainerScreenHandler.createGeneric9x3(syncId, inventory, player2.getEnderChestInventory()), ChatFormatter.text("Enderchest of " + player2.getEntityName())));

            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    private static int loadStructure(CommandContext<ServerCommandSource> source, String filename) {
        try{
            var player = source.getSource().getPlayer();
             core.loadSchem(filename, player.getWorld(), player.getBlockX(), player.getBlockY(), player.getBlockZ());
            Infinium.getInstance().LOGGER.info("Generated ${} at: ${}, ${}, ${}", filename, player.getBlockX(), player.getBlockY(), player.getBlockZ());
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
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7La cordura de &6&l" + name + " &7ha sido cambiada"), true);
            }

            if (players.size() > 1) {
                source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7La cordura de todos los jugadores ha sido cambiada"), true);
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
            Infinium.getInstance().getDateUtils().setCurrentDay(newDay);
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7El día se ha cambiado a: &6&l" + newDay), true);
            return 1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private static int getDay(CommandContext<ServerCommandSource> source) {
        try {
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7El día actual es: &6&l" + Infinium.getInstance().getDateUtils().getCurrentDay()), false);
            return 1;
        }catch (Exception ex){
            ex.printStackTrace();
            return -1;
        }
    }

    private static int getTotems(CommandContext<ServerCommandSource> source, ServerPlayerEntity player) {
        try{

            var data = ((EntityDataSaver) player).getPersistentData();
            int totems = data.getInt("infinium.totems");

            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&6&l" + player.getEntityName() + " &7ha consumido &6&l" + totems + " &7Tótems de la inmortalidad"), false);
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private static int setTotems(CommandContext<ServerCommandSource> source, ServerPlayerEntity player, int value) {
        try {
            var attributeInstance = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attributeInstance == null) return -1;
            attributeInstance.getModifiers().forEach(attributeInstance::removeModifier);

            var data = ((EntityDataSaver) player).getPersistentData();
            var totemString = "infinium.totems";
            data.putInt(totemString, value);

            if (value >= 30) {
                if (!attributeInstance.hasModifier(finalTotemDebuff)) attributeInstance.addPersistentModifier(finalTotemDebuff);

            } else if (value >= 25) {
                attributeInstance.removeModifier(finalTotemDebuff);
                if (!attributeInstance.hasModifier(secondTotemDebuff)) attributeInstance.addPersistentModifier(secondTotemDebuff);

            } else if(value >= 15) {
                attributeInstance.removeModifier(finalTotemDebuff);
                attributeInstance.removeModifier(secondTotemDebuff);
                if (!attributeInstance.hasModifier(firstTotemDebuff)) attributeInstance.addPersistentModifier(firstTotemDebuff);

            }

            player.setHealth(player.getMaxHealth());
            source.getSource().sendFeedback(ChatFormatter.textWithPrefix("&7Los totems de &6&l" + player.getEntityName() + " &7han sido cambiados a &6&l" + value), true);
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

        source.getSource().sendFeedback(ChatFormatter.text("&7Quedan " + eclipseManager.getTimeToString() + "&7 de Solar Eclipse."), false);
        return 1;
    }


}
