package me.bagel.spleef.game.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import me.bagel.spleef.generation.MapGenerator;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.arguments.BlockStateArgument;

public class GeneratePlatformCommand {

    public static ArgumentBuilder<CommandSource, ?> register() {
        return Commands.literal("generate")
        		.then(Commands.literal("sphere")
        		.then(Commands.argument("pos", BlockPosArgument.blockPos())
        		.then(Commands.argument("state", BlockStateArgument.blockState())
        		.then(Commands.argument("width", IntegerArgumentType.integer())
        		.then(Commands.argument("height", IntegerArgumentType.integer()).executes(context -> {
        			MapGenerator.makeCylinder(context.getSource().getWorld(), BlockPosArgument.getBlockPos(context, "pos"), BlockStateArgument.getBlockState(context, "state"), IntegerArgumentType.getInteger(context, "width"), IntegerArgumentType.getInteger(context, "height"));
        			return Command.SINGLE_SUCCESS;
        }))))));
    }
}