package com.oxtore.staff.mappers;

import com.oxtore.staff.DTOs.AddStaffRequest;
import com.oxtore.staff.DTOs.StoreStaffResponse;
import com.oxtore.staff.entity.StoreStaff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoreStaffMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "addedBy", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StoreStaff toEntity(AddStaffRequest request);

    StoreStaffResponse toResponse(StoreStaff storeStaff);
}