package com.fancyinnovations.fancycore.api.permissions;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Group {

    String getName();

    int getWeight();

    void setWeight(int weight);

    List<String> getParents();

    void addParent(String parent);

    void removeParent(String parent);

    void clearParents();

    String getPrefix();

    void setPrefix(String prefix);

    String getSuffix();

    void setSuffix(String suffix);

    /**
     * Gets the permissions directly assigned to this group.
     *
     * @return List of permissions
     */
    List<Permission> getPermissions();

    void setPermissions(List<Permission> permissions);

    /**
     * Gets all permissions including inherited ones from parent groups.
     *
     * @return List of all permissions
     */
    List<Permission> getAllPermissions();

    void setPermission(String permission, boolean enabled);

    void removePermission(String permission);

    Map<String, Object> getMetadata();

    void setMetadata(Map<String, Object> metadata);

    /**
     * Gets the metadata value for the specified key directly from this group.
     *
     * @param key Metadata key
     * @return Metadata value or null if not found
     */
    Object getMetadataValue(String key);

    /**
     * Gets the metadata value for the specified key, checking parent groups if not found in this group.
     *
     * @param key Metadata key
     * @return Metadata value or null if not found
     */
    Object getMetadataValueInherited(String key);

    void setMetadataValue(String key, Object value);

    void removeMetadataValue(String key);

    List<UUID> getMembers();

    void addMember(UUID memberUUID);

    void removeMember(UUID memberUUID);

    void clearMembers();

    boolean checkPermission(String permission);
}
