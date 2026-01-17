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

public class UnbanCMD extends CommandBase {

    protected final RequiredArg<FancyPlayer> targetArg = this.withRequiredArg("target", "The player to unban", FancyCoreArgs.PLAYER);

    public UnbanCMD() {
        super("unban", "Unban a player from the server");
        requirePermission("fancycore.commands.unban");
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
            if (punishment.type() == PunishmentType.BAN && punishment.isActive()) {
                foundPunishment = punishment;
                break;
            }
        }

        if (foundPunishment == null) {
            fp.sendMessage("Player " + target.getData().getUsername() + " is not banned.");
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

        fp.sendMessage("Player " + target.getData().getUsername() + " has been unbanned.");
    }
}
