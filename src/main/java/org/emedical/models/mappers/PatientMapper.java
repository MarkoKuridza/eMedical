package org.emedical.models.mappers;

import org.emedical.models.dto.Patient;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.PatientEntity;

public class PatientMapper {
 
    public static Patient toDto(PatientEntity entity) {
        if (entity == null) return null;

        Patient dto = new Patient();
        dto.setId(entity.getId());
        dto.setFirst_name(entity.getFirst_name());
        dto.setLast_name(entity.getLast_name());

        if (entity.getDoctor() != null) {
            dto.setDoctor_id(entity.getDoctor().getId());
        }

        return dto;
    }

    public static PatientEntity toEntity(Patient dto) {
        if (dto == null) return null;

        PatientEntity entity = new PatientEntity();
        entity.setId(dto.getId());
        entity.setFirst_name(dto.getFirst_name());
        entity.setLast_name(dto.getLast_name());

        //Ovdje treba da se postavi doktor za pacijenta ali to treba odraditi u Service sloju!!!!

        return entity;
    }

    public static void updateEntity(PatientEntity entity, Patient dto) {
        if (dto.getFirst_name() != null) {
            entity.setFirst_name(dto.getFirst_name());
        }
        if (dto.getLast_name() != null) {
            entity.setLast_name(dto.getLast_name());
        }
    }
}
