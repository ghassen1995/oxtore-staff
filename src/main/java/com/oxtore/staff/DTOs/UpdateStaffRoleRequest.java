package com.oxtore.staff.DTOs;

import com.oxtore.staff.enums.StaffRole;
import jakarta.validation.constraints.NotNull;

public record UpdateStaffRoleRequest(
        @NotNull StaffRole newRole
) {}