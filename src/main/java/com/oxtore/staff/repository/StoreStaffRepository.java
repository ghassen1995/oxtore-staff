package com.oxtore.staff.repository;

import com.oxtore.staff.entity.StoreStaff;
import com.oxtore.staff.enums.StaffRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreStaffRepository extends JpaRepository<StoreStaff, Long> {

    Optional<StoreStaff> findByStoreIdAndUserId(Long storeId, Long userId);

    List<StoreStaff> findAllByStoreId(Long storeId);

    List<StoreStaff> findAllByStoreIdAndRole(Long storeId, StaffRole role);

    boolean existsByStoreIdAndRole(Long storeId, StaffRole role);

    boolean existsByStoreIdAndUserId(Long storeId, Long userId);
}