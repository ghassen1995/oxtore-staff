package com.oxtore.staff.DTOs;

import com.oxtore.staff.enums.StaffRole;

import java.time.LocalDateTime;

public record StoreStaffResponse(
        Long id,
        Long storeId,
        Long userId,
        StaffRole role,
        Long addedBy,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}