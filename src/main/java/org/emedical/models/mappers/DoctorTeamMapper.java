package org.emedical.models.mappers;

import java.util.Set;
import java.util.stream.Collectors;

import org.emedical.models.dto.DoctorTeam;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.DoctorTeamEntity;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.entities.PatientEntity;
import org.emedical.models.requests.DoctorTeamRequest;

public class DoctorTeamMapper {
    
    public static DoctorTeamEntity toEntity(DoctorTeamRequest dto, DoctorEntity doctor, Set<NurseEntity> nurses, Set<PatientEntity> patients) {
        DoctorTeamEntity entity = new DoctorTeamEntity();
        entity.setDoctor(doctor);
        entity.setNurses(nurses);
        entity.setPatients(patients);
        return entity;
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
