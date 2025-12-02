package org.emedical.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.DoctorTeam;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.DoctorTeamEntity;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.entities.PatientEntity;
import org.emedical.models.mappers.DoctorMapper;
import org.emedical.models.mappers.DoctorTeamMapper;
import org.emedical.models.mappers.NurseMapper;
import org.emedical.models.mappers.PatientMapper;
import org.emedical.models.requests.DoctorTeamRequest;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.repositories.DoctorTeamRepository;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.repositories.PatientEntityRepository;
import org.emedical.service.DoctorTeamService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorTeamServiceImpl implements DoctorTeamService {
    
    private final DoctorTeamRepository repository;

    private final DoctorEntityRepository doctorRepository;
    private final NurseEntityRepository nurseRepository;
    private final PatientEntityRepository patientRepository;

    private final ModelMapper modelMapper;

    @Override
    public List<DoctorTeam> findAll() {
        return repository.findAll().stream().map(a -> modelMapper.map(a, DoctorTeam.class)).collect(Collectors.toList());
    }

    public DoctorTeam findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), DoctorTeam.class);
    }

    public DoctorTeam saveTeam(DoctorTeamRequest request) {
        DoctorEntity doctorEntity = doctorRepository.findById(request.getDoctorId())
            .orElseThrow(() -> new EntityNotFoundException("Doctor not found"));

        Set<NurseEntity> nurseEntities = new HashSet<>();
        if (request.getNurseIds() != null && !request.getNurseIds().isEmpty()) {
            nurseEntities = new HashSet<>(nurseRepository.findAllById(request.getNurseIds()));
        }

        Set<PatientEntity> patientEntities = new HashSet<>();
        if (request.getPatientIds() != null && !request.getPatientIds().isEmpty()) {
            patientEntities = new HashSet<>(patientRepository.findAllById(request.getPatientIds()));
        }

        DoctorTeamEntity teamEntity = new DoctorTeamEntity();
        teamEntity.setId(request.getTeamId());

        teamEntity.setDoctor(doctorEntity);
        teamEntity.setNurses(nurseEntities);
        teamEntity.setPatients(patientEntities);

        for (PatientEntity patient : patientEntities) {
            patient.setDoctor(doctorEntity);
            patient.setDoctorTeam(teamEntity);
        }

        DoctorTeamEntity savedEntity = repository.save(teamEntity);

        return DoctorTeamMapper.toDto(savedEntity);
    }

    public DoctorTeam editTeam(DoctorTeam team) {
        DoctorTeamEntity existingTeam = repository.findById(team.getId())
        .orElseThrow(() -> new IllegalArgumentException("This team does not exist!"));

        DoctorEntity doctorEntity = DoctorMapper.toEntity(team.getDoctor());
        existingTeam.setDoctor(doctorEntity);

        existingTeam.getNurses().clear();
        if (team.getNurses() != null) {
            Set<NurseEntity> nurseEntities = team.getNurses().stream()
                .map(NurseMapper::toEntity)
                .collect(Collectors.toSet());
            existingTeam.getNurses().addAll(nurseEntities);
        }

        for (PatientEntity oldPatient : existingTeam.getPatients()) {
            oldPatient.setDoctorTeam(null);
        }
        existingTeam.getPatients().clear();

        if (team.getPatients() != null) {
            Set<PatientEntity> patientEntities = team.getPatients().stream()
                .map(PatientMapper::toEntity)
                .collect(Collectors.toSet());
            for (PatientEntity patient : patientEntities) {
                patient.setDoctorTeam(existingTeam);
                patient.setDoctor(doctorEntity);
            }
            existingTeam.getPatients().addAll(patientEntities);
        }

        DoctorTeamEntity updated = repository.save(existingTeam);

        return DoctorTeamMapper.toDto(updated);
    }

    public void addDoctor(Integer team_id, Integer doctor_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        DoctorEntity doctorEntity = doctorRepository.findById(doctor_id)
        .orElseThrow(() -> new IllegalArgumentException("Doctor not found with ID: " + doctor_id));

        teamEntity.setDoctor(doctorEntity);

        repository.save(teamEntity);
    }

    public void removeDoctor(Integer team_id, Integer doctor_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        if (teamEntity.getDoctor() != null && teamEntity.getDoctor().getId().equals(doctor_id)) {
            teamEntity.setDoctor(null);
            repository.save(teamEntity);
        } else {
            throw new IllegalArgumentException("Doctor with ID " + doctor_id + " is not assigned to team " + team_id);
        }
    }


    public void addNurse(Integer team_id, Integer nurse_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        NurseEntity nurseEntity = nurseRepository.findById(nurse_id)
        .orElseThrow(() -> new IllegalArgumentException("Nurse not found with ID: " + nurse_id));

        teamEntity.getNurses().add(nurseEntity);

        repository.save(teamEntity);
    }

    public void removeNurse(Integer team_id, Integer nurse_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
        .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        boolean removed = teamEntity.getNurses().removeIf(n -> n.getId().equals(nurse_id));
        if (removed) {
            repository.save(teamEntity);
        } else {
            throw new IllegalArgumentException("Nurse with ID " + nurse_id + " is not assigned to team " + team_id);
        }
    }

    public void addPatient(Integer team_id, Integer patient_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        PatientEntity patientEntity = patientRepository.findById(patient_id)
        .orElseThrow(() -> new IllegalArgumentException("Patient not found with ID: " + patient_id));

        DoctorEntity doctorEntity = teamEntity.getDoctor();
        if(doctorEntity == null) {
            throw new IllegalStateException("Team does not have an assigned doctor.");
        }

        patientEntity.setDoctorTeam(teamEntity);
        patientEntity.setDoctor(doctorEntity);
        teamEntity.getPatients().add(patientEntity);

        patientRepository.save(patientEntity);
        repository.save(teamEntity);
    }

    public void removePatient(Integer team_id, Integer patient_id) {
        DoctorTeamEntity teamEntity = repository.findById(team_id)
            .orElseThrow(() -> new IllegalArgumentException("Team not found with ID: " + team_id));

        PatientEntity toRemove = teamEntity.getPatients().stream()
            .filter(p -> p.getId().equals(patient_id))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Patient with ID " + patient_id + " is not assigned to team " + team_id));

        toRemove.setDoctorTeam(null);
        teamEntity.getPatients().remove(toRemove);

        repository.save(teamEntity);
    }
}
