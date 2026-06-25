package com.oxtore.staff.kafka;

import com.oxtore.staff.enums.StaffRole;
import com.oxtore.staff.event.StoreCreatedEvent;
import com.oxtore.staff.service.StoreStaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StoreCreatedConsumer {

    private final StoreStaffService storeStaffService;

    @KafkaListener(topics = "store.created", groupId = "staff-service")
    @Transactional
    public void handleStoreCreated(StoreCreatedEvent event) {
        storeStaffService.addStaff(
                event.storeId(),
                event.ownerId(),
                StaffRole.SUPERVISOR,
                event.ownerId() // supervisor adds themselves
        );
        log.info("Supervisor created for storeId={} ownerId={}", event.storeId(), event.ownerId());
    }
}