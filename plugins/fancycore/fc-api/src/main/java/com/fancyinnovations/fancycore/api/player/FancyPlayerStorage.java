package com.fancyinnovations.fancycore.api.player;

import java.util.List;
import java.util.UUID;

public interface FancyPlayerStorage {

    void savePlayer(FancyPlayer player);

    FancyPlayer loadPlayer(UUID uuid);
    List<FancyPlayer> loadAllPlayers();

    void deletePlayer(UUID uuid);

    int countPlayers();
}
