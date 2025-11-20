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

        return tryToGetFromStorage(uuid);
    }

    @Override
    public FancyPlayer getByUsername(String username) {
        for (FancyPlayer fp : cache.values()) {
            if (fp.getUsername().equalsIgnoreCase(username)) {
                return fp;
            }
        }

        return tryToGetFromStorage(username);
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
        try {
            FancyPlayer fancyPlayer = storage.loadPlayerByUsername(username);
            addPlayerToCache(fancyPlayer);
            return fancyPlayer;
        } catch (Exception e) {
            return null;
        }
    }

    public void removePlayerFromCache(UUID uuid) {
        cache.remove(uuid);
    }

}
