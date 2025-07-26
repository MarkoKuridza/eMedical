package org.emedical.models.mappers;

import org.emedical.models.dto.Doctor;
import org.emedical.models.entities.DoctorEntity;

public class DoctorMapper {
    
    public static Doctor toDto(DoctorEntity entity) {
        if (entity == null) return null;

        Doctor dto = new Doctor();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setIsActive(entity.getIsActive());

        dto.setFirst_name(entity.getFirst_name());
        dto.setLast_name(entity.getLast_name());
        dto.setSpecialization(entity.getSpecialization());

        dto.setAppointments(entity.getAppointments());

        return dto;
    }

    public static DoctorEntity toEntity(Doctor dto) {
        if (dto == null) return null;

        DoctorEntity entity = new DoctorEntity();
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setIsActive(dto.getIsActive());

        entity.setFirst_name(dto.getFirst_name());
        entity.setLast_name(dto.getLast_name());
        entity.setSpecialization(dto.getSpecialization());

        //TODO Kasnije
        //entity.setAppointments(dto.getAppointments());

        return entity;
    }

    public static void updateEntity(DoctorEntity entity, Doctor dto) {
        if (dto.getFirst_name() != null) entity.setFirst_name(dto.getFirst_name());
        if (dto.getLast_name() != null) entity.setLast_name(dto.getLast_name());
        if (dto.getSpecialization() != null) entity.setSpecialization(dto.getSpecialization());

        if (dto.getUsername() != null) entity.setUsername(dto.getUsername());
        if (dto.getPassword() != null) entity.setPassword(dto.getPassword());
        if (dto.getRole() != null) entity.setRole(dto.getRole());
        if (dto.getIsActive() != null) entity.setIsActive(dto.getIsActive());

        if (dto.getAppointments() != null) entity.setAppointments(dto.getAppointments());
    }
}
