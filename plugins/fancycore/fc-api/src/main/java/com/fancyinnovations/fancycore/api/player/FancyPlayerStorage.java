package com.fancyinnovations.fancycore.api.player;

import java.util.List;
import java.util.UUID;

public interface FancyPlayerStorage {

    void savePlayer(FancyPlayerData player);

    FancyPlayerData loadPlayer(UUID uuid);
    FancyPlayerData loadPlayerByUsername(String username);
    List<FancyPlayerData> loadAllPlayers();

    void deletePlayer(UUID uuid);

    int countPlayers();
}
