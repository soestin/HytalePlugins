package com.fancyinnovations.fancycore.commands.inventory;

import com.fancyinnovations.fancycore.api.inventory.Kit;
import com.fancyinnovations.fancycore.api.inventory.KitsService;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.ParseResult;
import com.hypixel.hytale.server.core.command.system.arguments.types.SingleArgumentType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryArgs {

    public static final SingleArgumentType<Kit> KIT = new SingleArgumentType<Kit>("Kit", "The name of the kit", new String[]{"pvp", "blocks"}) {
        public @Nullable Kit parse(@Nonnull String input, @Nonnull ParseResult parseResult) {
            Kit kit = KitsService.get().getKit(input);
            if (kit == null) {
                parseResult.fail(Message.raw("Kit '" + input + "' not found."));
                return null;
            }

            return kit;
        }
    };

}
