package com.oxtore.staff.service;

import com.oxtore.staff.entity.StoreStaff;
import com.oxtore.staff.enums.StaffRole;
import com.oxtore.staff.exceptions.InvalidStaffOperationException;
import com.oxtore.staff.exceptions.StaffAlreadyExistsException;
import com.oxtore.staff.exceptions.StaffNotFoundException;
import com.oxtore.staff.exceptions.SupervisorAlreadyExistsException;
import com.oxtore.staff.repository.StoreStaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreStaffServiceImpl implements StoreStaffService {

    private final StoreStaffRepository storeStaffRepository;

    @Transactional
    @Override
    public StoreStaff addStaff(Long storeId, Long userId, StaffRole role, Long addedBy) {

        if (storeStaffRepository.existsByStoreIdAndUserId(storeId, userId)) {
            throw new StaffAlreadyExistsException(
                    "User " + userId + " is already a staff member of store " + storeId);
        }

        if (role == StaffRole.SUPERVISOR && storeStaffRepository.existsByStoreIdAndRole(storeId, StaffRole.SUPERVISOR)) {
            throw new SupervisorAlreadyExistsException(
                    "Store " + storeId + " already has a supervisor");
        }

        StoreStaff staff = StoreStaff.builder()
                .storeId(storeId)
                .userId(userId)
                .role(role)
                .addedBy(addedBy)
                .build();

        return storeStaffRepository.save(staff);
    }

    @Transactional
    @Override
    public StoreStaff updateRole(Long storeId, Long userId, StaffRole newRole, Long updatedBy) {

        StoreStaff staff = storeStaffRepository.findByStoreIdAndUserId(storeId, userId)
                .orElseThrow(() -> new StaffNotFoundException(
                        "Staff member not found for storeId=" + storeId + " userId=" + userId));

        if (staff.getRole() == StaffRole.SUPERVISOR) {
            throw new InvalidStaffOperationException("Supervisor role cannot be changed");
        }

        if (newRole == StaffRole.SUPERVISOR) {
            throw new InvalidStaffOperationException("Cannot promote to supervisor via role update");
        }

        staff.setRole(newRole);
        return storeStaffRepository.save(staff);
    }

    @Transactional
    @Override
    public void removeStaff(Long storeId, Long userId, Long removedBy) {

        StoreStaff staff = storeStaffRepository.findByStoreIdAndUserId(storeId, userId)
                .orElseThrow(() -> new StaffNotFoundException(
                        "Staff member not found for storeId=" + storeId + " userId=" + userId));

        if (staff.getRole() == StaffRole.SUPERVISOR) {
            throw new InvalidStaffOperationException("Supervisor cannot be removed from a store");
        }

        storeStaffRepository.delete(staff);
        log.info("Staff member userId={} removed from storeId={} by userId={}", userId, storeId, removedBy);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStaff> getStaffByStore(Long storeId) {
        return storeStaffRepository.findAllByStoreId(storeId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<StoreStaff> getStaffByStoreAndRole(Long storeId, StaffRole role) {
        return storeStaffRepository.findAllByStoreIdAndRole(storeId, role);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<StoreStaff> getStaffMember(Long storeId, Long userId) {
        return storeStaffRepository.findByStoreIdAndUserId(storeId, userId);
    }
}