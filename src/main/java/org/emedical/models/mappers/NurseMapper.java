package org.emedical.models.mappers;

import org.emedical.models.dto.Nurse;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.enums.Role;

public class NurseMapper {
    

    public static Nurse toDto(NurseEntity entity) {
        if (entity == null) return null;

        Nurse dto = new Nurse();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setIsActive(entity.getIsActive());

        dto.setFirst_name(entity.getFirst_name());
        dto.setLast_name(entity.getLast_name());

        return dto;
    }

    public static NurseEntity toEntity(Nurse dto) {
        if (dto == null) return null;

        NurseEntity entity = new NurseEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(Role.NURSE);
        entity.setIsActive(dto.getIsActive());

        entity.setFirst_name(dto.getFirst_name());
        entity.setLast_name(dto.getLast_name());

        return entity;
    }

    public static void updateEntity(NurseEntity entity, Nurse dto) {
        if (dto.getFirst_name() != null) entity.setFirst_name(dto.getFirst_name());
        if (dto.getLast_name() != null) entity.setLast_name(dto.getLast_name());

        if (dto.getUsername() != null) entity.setUsername(dto.getUsername());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getRole() != null) entity.setRole(Role.NURSE);
        if (dto.getIsActive() != null) entity.setIsActive(dto.getIsActive());
    }
}
