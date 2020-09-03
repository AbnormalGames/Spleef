package me.bagel.spleef.game.command;

import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;

public class SpleefCommand {
    public static void register(CommandDispatcher<CommandSource> dispatcher)
    {
        dispatcher.register(Commands.literal("spleef")
                .requires(source -> source.hasPermissionLevel(4))
                .then(GeneratePlatformCommand.register()));
    }
}
