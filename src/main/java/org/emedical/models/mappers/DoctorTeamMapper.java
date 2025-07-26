package org.emedical.models.mappers;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.emedical.models.dto.DoctorTeam;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.DoctorTeamEntity;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.entities.PatientEntity;
import org.emedical.models.requests.DoctorTeamRequest;

public class DoctorTeamMapper {
    
    public static DoctorTeamEntity toEntity(DoctorTeam dto, DoctorEntity doctor, Set<NurseEntity> nurses, Set<PatientEntity> patients) {
        DoctorTeamEntity entity = new DoctorTeamEntity();
        
        entity.setId(dto.getId());
        entity.setDoctor(doctor);
        entity.setNurses(nurses != null ? nurses : Collections.emptySet());

        if(patients != null) {
            entity.setPatients(patients);
            for(PatientEntity patient : patients) {
                patient.setDoctorTeam(entity);
            }
        } else {
            entity.setPatients(Collections.emptySet());
        }
        return entity;
    }

    public static DoctorTeamRequest toRequest(DoctorTeamEntity entity) {
        DoctorTeamRequest dto = new DoctorTeamRequest();
        dto.setTeamId(entity.getId());
        dto.setDoctorId(entity.getDoctor().getId());
        dto.setNurseIds(entity.getNurses().stream().map(NurseEntity::getId).collect(Collectors.toSet()));
        dto.setPatientIds(entity.getPatients().stream().map(PatientEntity::getId).collect(Collectors.toSet()));
        return dto;
    }

    public static DoctorTeam toDto(DoctorTeamEntity entity) {
        DoctorTeam dto = new DoctorTeam();
        dto.setId(entity.getId());
        dto.setDoctor(DoctorMapper.toDto(entity.getDoctor()));
        dto.setNurses(entity.getNurses().stream().map(NurseMapper::toDto).collect(Collectors.toSet()));
        dto.setPatients(entity.getPatients().stream().map(PatientMapper::toDto).collect(Collectors.toSet()));
        return dto;
    }
}
