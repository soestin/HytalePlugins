package com.fancyinnovations.fancycore.api.punishments;

import java.util.UUID;

public interface Punishment {

    UUID id();

    UUID player();

    PunishmentType type();

    String reason();

    long issuedAt();

    UUID issuedBy();

    long expiresAt();

    long remainingDuration();

    boolean isActive();
}
