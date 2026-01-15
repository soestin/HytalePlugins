package com.fancyinnovations.fancycore.main;

import com.fancyinnovations.fancycore.api.chat.ChatService;
import com.fancyinnovations.fancycore.api.economy.Currency;
import com.fancyinnovations.fancycore.api.economy.CurrencyService;
import com.fancyinnovations.fancycore.api.inventory.Kit;
import com.fancyinnovations.fancycore.api.inventory.KitsService;
import com.fancyinnovations.fancycore.api.permissions.Group;
import com.fancyinnovations.fancycore.api.permissions.PermissionService;
import com.fancyinnovations.fancycore.permissions.GroupImpl;
import com.fancyinnovations.fancycore.permissions.PermissionImpl;
import com.hypixel.hytale.server.core.inventory.ItemStack;

import java.io.File;
import java.util.HashSet;
import java.util.List;

public class SeedDefaultData {

    public static void seed() {
        File configFile = new File("mods/FancyCore/data/config.json");
        if (configFile.exists()) {
            return;
        }
        configFile.getParentFile().mkdirs();

        seedChatRooms();
        seedGroups();
        seedEconomy();
        seedEconomy();
    }

    private static void seedChatRooms() {
        ChatService.get().createChatRoom("global");
        ChatService.get().createChatRoom("staff");
    }

    private static void seedGroups() {
        Group memberGroup = new GroupImpl(
                "member",
                0,
                new HashSet<>(),
                "&8[&7Member&8]",
                "",
                List.of(),
                new HashSet<>()
        );
        PermissionService.get().addGroup(memberGroup);

        Group moderatorGroup = new GroupImpl(
                "moderator",
                100,
                new HashSet<>(List.of("member")),
                "&2[&a&lMOD&2]",
                "",
                List.of(),
                new HashSet<>()
        );
        PermissionService.get().addGroup(moderatorGroup);

        Group adminGroup = new GroupImpl(
                "admin",
                200,
                new HashSet<>(List.of("moderator")),
                "&e[&6&lADMIN&e]",
                "",
                List.of(
                        new PermissionImpl("*", true)
                ),
                new HashSet<>()
        );
        PermissionService.get().addGroup(adminGroup);

        Group ownerGroup = new GroupImpl(
                "owner",
                300,
                new HashSet<>(),
                "&4[&c&lOWNER&4]",
                "",
                List.of(
                        new PermissionImpl("*", true)
                ),
                new HashSet<>()
        );
        PermissionService.get().addGroup(ownerGroup);
    }

    private static void seedEconomy() {
        Currency primaryCurrency = new Currency(
                "Dollar",
                "$",
                2
        );
        CurrencyService.get().registerCurrency(primaryCurrency);
    }

    private static void seedKits() {
        Kit starterKit = new Kit(
                "starter",
                "Starter Kit",
                "A kit for new players."
        );
        List<ItemStack> starterKitItems = List.of(
                new ItemStack("Bench_WorkBench", 1),
                new ItemStack("Food_Break", 20),
                new ItemStack("Weapon_Sword_Wood", 1)
        );
        KitsService.get().createKit(starterKit, starterKitItems);
    }

}
