package com.fancyinnovations.fancycore.permissions;

import com.fancyinnovations.fancycore.api.permissions.Permission;

public class PermissionImpl implements Permission {

    private final String permission;
    private final boolean enabled;

    public PermissionImpl(String permission, boolean enabled) {
        this.permission = permission;
        this.enabled = enabled;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
