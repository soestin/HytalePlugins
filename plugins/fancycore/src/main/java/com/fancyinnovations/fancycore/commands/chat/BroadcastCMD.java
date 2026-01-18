package com.fancyinnovations.fancycore.commands.chat;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.arguments.types.ArgTypes;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BroadcastCMD extends CommandBase {

    protected final RequiredArg<List<String>> messageArg = this.withListRequiredArg("message", "The broadcast message to send", ArgTypes.STRING);

    public BroadcastCMD() {
        super("broadcast", "Broadcast a message to all players");
        addAliases("bc", "announce");
        requirePermission("fancycore.commands.broadcast");
        setAllowsExtraArguments(true);
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer sender = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (sender == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        String[] parts = ctx.getInputString().split(" ");
        StringBuilder messageBuilder = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            messageBuilder.append(parts[i]);
            if (i < parts.length - 1) {
                messageBuilder.append(" ");
            }
        }
        String message = messageBuilder.toString();

        for (FancyPlayer onlinePlayer : FancyPlayerService.get().getOnlinePlayers()) {
            onlinePlayer.sendMessage(message);
        }
    }
}
