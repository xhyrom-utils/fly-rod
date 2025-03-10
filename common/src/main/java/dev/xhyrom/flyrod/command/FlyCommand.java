package dev.xhyrom.flyrod.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class FlyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("fly")
                        .requires(source -> source.hasPermission(2))
                        .executes(FlyCommand::toggle)
                        .then(
                                Commands.literal("speed")
                                        .then(
                                                Commands.argument("speed", FloatArgumentType.floatArg())
                                                        .executes(context -> flySpeed(context, context.getSource().getPlayerOrException(), context.getArgument("speed", Float.class)))
                                        )
                        )
        );
    }

    private static int toggle(CommandContext<CommandSourceStack> source) throws CommandSyntaxException {
        ServerPlayer player = source.getSource().getPlayerOrException();
        return player.getAbilities().mayfly ? disallow(source, player) : allow(source, player);
    }

    private static int allow(CommandContext<CommandSourceStack> source, ServerPlayer player) {
        player.getAbilities().mayfly = true;
        player.onUpdateAbilities();

        source.getSource().sendSuccess(() -> Component.translatable("command.fly.success.allow.single", player.getName()), true);

        return 1;
    }

    private static int disallow(CommandContext<CommandSourceStack> source, ServerPlayer player) {
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.onUpdateAbilities();

        source.getSource().sendSuccess(() -> Component.translatable("command.fly.success.noallow.single", player.getName()), true);

        return 1;
    }

    private static int flySpeed(CommandContext<CommandSourceStack> source, ServerPlayer player, float speed) {
        player.getAbilities().setFlyingSpeed(speed);
        player.onUpdateAbilities();

        source.getSource().sendSuccess(() -> Component.translatable("command.fly.success.setflyspeed.single", player.getName(), speed), true);

        return 1;
    }
}
