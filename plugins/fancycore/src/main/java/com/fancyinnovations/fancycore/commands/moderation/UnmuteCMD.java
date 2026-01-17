package com.fancyinnovations.fancycore.commands.moderation;

import com.fancyinnovations.fancycore.api.moderation.Punishment;
import com.fancyinnovations.fancycore.api.moderation.PunishmentService;
import com.fancyinnovations.fancycore.api.moderation.PunishmentType;
import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.commands.arguments.FancyCoreArgs;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.fancyinnovations.fancycore.moderation.PunishmentImpl;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.arguments.system.RequiredArg;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnmuteCMD extends CommandBase {

    protected final RequiredArg<FancyPlayer> targetArg = this.withRequiredArg("target", "The player to unmute", FancyCoreArgs.PLAYER);

    public UnmuteCMD() {
        super("unmute", "Unmute a player from the server");
        requirePermission("fancycore.commands.unmute");
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        if (!ctx.isPlayer()) {
            ctx.sendMessage(Message.raw("This command can only be executed by a player."));
            return;
        }

        FancyPlayer fp = FancyPlayerService.get().getByUUID(ctx.sender().getUuid());
        if (fp == null) {
            ctx.sendMessage(Message.raw("FancyPlayer not found."));
            return;
        }

        FancyPlayer target = targetArg.get(ctx);

        Punishment foundPunishment = null;
        List<Punishment> punishments = PunishmentService.get().getPunishmentsForPlayer(target);
        for (Punishment punishment : punishments) {
            if (punishment.type() == PunishmentType.MUTE && punishment.isActive()) {
                foundPunishment = punishment;
                break;
            }
        }

        if (foundPunishment == null) {
            fp.sendMessage("Player " + target.getData().getUsername() + " is not muted.");
            return;
        }

        Punishment updatedPunishment = new PunishmentImpl(
                foundPunishment.id(),
                foundPunishment.player(),
                foundPunishment.type(),
                foundPunishment.reason(),
                foundPunishment.issuedAt(),
                foundPunishment.issuedBy(),
                System.currentTimeMillis() - 1000
        );

        FancyCorePlugin.get().getPunishmentStorage().createPunishment(updatedPunishment);

        fp.sendMessage("Player " + target.getData().getUsername() + " has been unmuted.");
        target.sendMessage("You have been unmuted.");
    }
}
