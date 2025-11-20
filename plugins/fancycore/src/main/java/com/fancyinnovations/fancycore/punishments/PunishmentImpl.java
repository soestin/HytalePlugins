package com.fancyinnovations.fancycore.punishments;

import com.fancyinnovations.fancycore.api.punishments.Punishment;
import com.fancyinnovations.fancycore.api.punishments.PunishmentType;
import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public record PunishmentImpl(
    UUID id,
    UUID player,
    PunishmentType type,
    String reason,
    @SerializedName("issued_at") long issuedAt,
    @SerializedName("issued_by") UUID issuedBy,
    @SerializedName("expires_at") long expiresAt
) implements Punishment {

    public long remainingDuration() {
        if (expiresAt <= 0) {
            return -1; // Permanent punishment
        }
        return expiresAt - System.currentTimeMillis();
    }

    public boolean isActive() {
        return expiresAt <= 0 || System.currentTimeMillis() < expiresAt;
    }

}
