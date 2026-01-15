package com.fancyinnovations.fancycore.inventory.service;

import com.fancyinnovations.fancycore.api.inventory.Kit;
import com.fancyinnovations.fancycore.api.inventory.KitsService;
import com.fancyinnovations.fancycore.api.inventory.KitsStorage;
import com.hypixel.hytale.server.core.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class KitsServiceImpl implements KitsService {

    private final KitsStorage storage;
    private final Map<String, Kit> kitsCache;
    private final Map<String, List<ItemStack>> kitItemsCache;

    public KitsServiceImpl(KitsStorage storage) {
        this.storage = storage;
        this.kitsCache = new ConcurrentHashMap<>();
        this.kitItemsCache = new ConcurrentHashMap<>();
        load();
    }

    private void load() {
        for (Kit kit : storage.getKits()) {
            kitsCache.put(kit.name(), kit);
            kitItemsCache.put(kit.name(), storage.getKitItems(kit.name()));
        }
    }

    @Override
    public Kit getKit(String name) {
        return kitsCache.get(name);
    }

    @Override
    public List<ItemStack> getKitItems(Kit kit) {
        return kitItemsCache.getOrDefault(kit.name(), List.of());
    }

    @Override
    public List<Kit> getAllKits() {
        return List.copyOf(kitsCache.values());
    }

    @Override
    public void createKit(Kit kit, List<ItemStack> items) {
        storage.storeKit(kit, items);
        kitsCache.put(kit.name(), kit);
        kitItemsCache.put(kit.name(), items);
    }

    @Override
    public void deleteKit(Kit kit) {
        storage.deleteKit(kit);
        kitsCache.remove(kit.name());
        kitItemsCache.remove(kit.name());
    }
}
