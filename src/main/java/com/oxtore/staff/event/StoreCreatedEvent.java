package com.oxtore.staff.event;

import java.time.LocalDateTime;

public record StoreCreatedEvent(
        Long storeId,
        Long ownerId,
        LocalDateTime occurredAt
) {}