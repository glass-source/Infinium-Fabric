package com.infinium.global.commands;

import com.infinium.api.utils.ChatFormatter;
import com.infinium.api.utils.EntityDataSaver;
import com.infinium.api.utils.Utils;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.server.command.ServerCommandSource;

import static com.infinium.api.utils.ChatFormatter.cd;

public class StaffCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated){
        var staff = cd("staff");
        dispatcher.register(staff.then(cd("setDays").executes(StaffCommand::setDays)));
    }

    private static int setDays(CommandContext<ServerCommandSource> source) {
        Utils.setDay(20);
        return 0;
    }

}
