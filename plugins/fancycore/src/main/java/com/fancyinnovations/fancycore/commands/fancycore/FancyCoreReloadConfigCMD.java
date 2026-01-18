package com.fancyinnovations.fancycore.commands.fancycore;

import com.fancyinnovations.fancycore.main.FancyCorePlugin;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.CommandBase;
import org.jetbrains.annotations.NotNull;

public class FancyCoreReloadConfigCMD extends CommandBase {

    protected FancyCoreReloadConfigCMD() {
        super("reloadconfig", "Reloads the FancyCore config.json file");
        requirePermission("fancycore.commands.fancycore.reloadconfig");
    }

    @Override
    protected void executeSync(@NotNull CommandContext ctx) {
        FancyCorePlugin.get().getConfig().reload();
        ctx.sendMessage(Message.raw("FancyCore config.json reloaded successfully!"));
    }
}
