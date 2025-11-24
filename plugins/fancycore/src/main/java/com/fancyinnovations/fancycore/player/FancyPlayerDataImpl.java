package com.fancyinnovations.fancycore.player;

import com.fancyinnovations.fancycore.api.permissions.Permission;
import com.fancyinnovations.fancycore.api.player.FancyPlayerData;
import com.fancyinnovations.fancycore.permissions.PermissionImpl;
import org.jetbrains.annotations.ApiStatus;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FancyPlayerDataImpl implements FancyPlayerData {

    private UUID uuid;
    private String username;
    private List<Permission> permissions;
    private List<UUID> groups;
    private String nickname;
    private Color chatColor;
    private double balance;
    private long firstLoginTime; // timestamp
    private long playTime; // in milliseconds

    private boolean isDirty;

    /**
     * Constructor for a new FancyPlayer
     */
    public FancyPlayerDataImpl(UUID uuid, String username) {
        this.uuid = uuid;
        this.username = username;
        this.permissions = new ArrayList<>();
        this.groups = new ArrayList<>();
        this.nickname = username; // default nickname is the username
        this.chatColor = Color.WHITE;
        this.balance = 0.0;
        this.firstLoginTime = System.currentTimeMillis();
        this.playTime = 0L;
        this.isDirty = true;
    }

    public FancyPlayerDataImpl(
            UUID uuid,
            String username,
            List<Permission> permissions,
            List<UUID> groups,
            String nickname,
            Color chatColor,
            double balance,
            long firstLoginTime,
            long playTime
    ) {
        this.uuid = uuid;
        this.username = username;
        this.permissions = permissions;
        this.groups = groups;
        this.nickname = nickname;
        this.chatColor = chatColor;
        this.balance = balance;
        this.firstLoginTime = firstLoginTime;
        this.playTime = playTime;
        this.isDirty = false;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public void setUUID(UUID uuid) {
        this.uuid = uuid;
        this.isDirty = true;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
        this.isDirty = true;
    }

    @Override
    public List<Permission> getPermissions() {
        return permissions;
    }

    @Override
    public void setPermissions(List<Permission> permissions) {
        this.permissions.clear();
        this.permissions.addAll(permissions);
        this.isDirty = true;
    }

    @Override
    public void setPermission(String permission, boolean enabled) {
        for (Permission p : this.permissions) {
            if (p.getPermission().equals(permission)) {
                p.setEnabled(enabled);
                return;
            }
        }

        this.permissions.add(new PermissionImpl(permission, enabled));
    }

    @Override
    public void removePermission(String permission) {
        this.permissions.removeIf(p -> p.getPermission().equals(permission));
    }

    @Override
    public List<UUID> getGroups() {
        return List.of();
    }

    @Override
    public void setGroups(List<UUID> groups) {

    }

    @Override
    public void addGroup(UUID group) {

    }

    @Override
    public void removeGroup(UUID group) {

    }

    @Override
    public String getNickname() {
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
        this.isDirty = true;
    }

    @Override
    public Color getChatColor() {
        return chatColor;
    }

    @Override
    public void setChatColor(Color chatColor) {
        this.chatColor = chatColor;
        this.isDirty = true;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
        this.isDirty = true;
    }

    @Override
    public void addBalance(double amount) {
        setBalance(this.balance + amount);
    }

    @Override
    public void removeBalance(double amount) {
        setBalance(this.balance - amount);
    }

    @Override
    public long getFirstLoginTime() {
        return firstLoginTime;
    }

    @ApiStatus.Internal
    public void setFirstLoginTime(long firstLoginTime) {
        this.firstLoginTime = firstLoginTime;
        this.isDirty = true;
    }

    @Override
    public long getPlayTime() {
        return playTime;
    }

    @ApiStatus.Internal
    public void setPlayTime(long playTime) {
        this.playTime = playTime;
        this.isDirty = true;
    }

    @Override
    public void addPlayTime(long additionalTime) {
        setPlayTime(this.playTime + additionalTime);
    }

    @Override
    public boolean isDirty() {
        return isDirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        isDirty = dirty;
    }

}
