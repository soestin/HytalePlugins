package com.fancyinnovations.fancycore.player.service;

import com.fancyinnovations.fancycore.api.player.FancyPlayer;
import com.fancyinnovations.fancycore.api.player.FancyPlayerService;
import com.fancyinnovations.fancycore.api.player.FancyPlayerStorage;
import com.fancyinnovations.fancycore.main.FancyCorePlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FancyPlayerServiceImpl implements FancyPlayerService {

    private final Map<UUID, FancyPlayer> cache;
    private final FancyPlayerStorage storage;

    public FancyPlayerServiceImpl() {
        this.cache = new ConcurrentHashMap<>();
        this.storage = FancyCorePlugin.get().getPlayerStorage();
    }

    @Override
    public FancyPlayer getByUUID(UUID uuid) {
        if (cache.containsKey(uuid)) {
            return cache.get(uuid);
        }

        FancyPlayer fancyPlayer = tryToGetFromStorage(uuid);

        return null;
    }

    @Override
    public FancyPlayer getByUsername(String username) {
        for (FancyPlayer fp : cache.values()) {
            if (fp.getUsername().equalsIgnoreCase(username)) {
                return fp;
            }
        }

        return null;
    }

    @Override
    public List<FancyPlayer> getAll() {
        return new ArrayList<>(cache.values());
    }

    public void addPlayerToCache(FancyPlayer player) {
        cache.put(player.getUUID(), player);
    }

    public FancyPlayer tryToGetFromStorage(UUID uuid) {
        try {
            FancyPlayer fancyPlayer = storage.loadPlayer(uuid);
            addPlayerToCache(fancyPlayer);
            return fancyPlayer;
        } catch (Exception e) {
            return null;
        }
    }

    public FancyPlayer tryToGetFromStorage(String username) {
        List<FancyPlayer> fancyPlayers = storage.loadAllPlayers();

        FancyPlayer found = null;
        for (FancyPlayer fp : fancyPlayers) {
            cache.put(fp.getUUID(), fp);

            if (fp.getUsername().equalsIgnoreCase(username)) {
                found = fp;
            }
        }


        return found;
    }

}
