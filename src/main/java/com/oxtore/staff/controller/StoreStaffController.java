package com.oxtore.staff.controller;

import com.oxtore.staff.DTOs.AddStaffRequest;
import com.oxtore.staff.DTOs.StoreStaffResponse;
import com.oxtore.staff.DTOs.UpdateStaffRoleRequest;
import com.oxtore.staff.entity.StoreStaff;
import com.oxtore.staff.enums.StaffRole;
import com.oxtore.staff.mappers.StoreStaffMapper;
import com.oxtore.staff.service.StoreStaffService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stores/{storeId}/staff")
@RequiredArgsConstructor
public class StoreStaffController {

    private final StoreStaffService storeStaffService;
    private final StoreStaffMapper storeStaffMapper;

    @PostMapping
    public ResponseEntity<StoreStaffResponse> addStaff(
            @PathVariable Long storeId,
            @Valid @RequestBody AddStaffRequest request) {
        // TODO: extract addedBy from security context once JWT is wired
        Long addedBy = request.userId(); // temporary
        StoreStaff saved = storeStaffService.addStaff(
                storeId, request.userId(), request.role(), addedBy);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(storeStaffMapper.toResponse(saved));
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<StoreStaffResponse> updateRole(
            @PathVariable Long storeId,
            @PathVariable Long userId,
            @Valid @RequestBody UpdateStaffRoleRequest request) {
        // TODO: extract updatedBy from security context
        StoreStaff updated = storeStaffService.updateRole(
                storeId, userId, request.newRole(), userId);
        return ResponseEntity.ok(storeStaffMapper.toResponse(updated));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> removeStaff(
            @PathVariable Long storeId,
            @PathVariable Long userId) {
        // TODO: extract removedBy from security context
        storeStaffService.removeStaff(storeId, userId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<StoreStaffResponse>> getStaffByStore(
            @PathVariable Long storeId) {
        List<StoreStaffResponse> staff = storeStaffService.getStaffByStore(storeId)
                .stream()
                .map(storeStaffMapper::toResponse)
                .toList();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<List<StoreStaffResponse>> getStaffByRole(
            @PathVariable Long storeId,
            @PathVariable StaffRole role) {
        List<StoreStaffResponse> staff = storeStaffService.getStaffByStoreAndRole(storeId, role)
                .stream()
                .map(storeStaffMapper::toResponse)
                .toList();
        return ResponseEntity.ok(staff);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<StoreStaffResponse> getStaffMember(
            @PathVariable Long storeId,
            @PathVariable Long userId) {
        return storeStaffService.getStaffMember(storeId, userId)
                .map(storeStaffMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}