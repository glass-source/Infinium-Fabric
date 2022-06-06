package com.infinium.global.commands;

import com.infinium.api.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static com.infinium.api.utils.ChatFormatter.cd;

public class StaffCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){

    }

    private static int setDays(CommandContext<ServerCommandSource> source, int day) {
        Utils.setDay(day);
        return 0;
    }

}
