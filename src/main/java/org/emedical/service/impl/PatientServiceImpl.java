package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Patient;
import org.emedical.models.entities.PatientEntity;
import org.emedical.models.mappers.PatientMapper;
import org.emedical.repositories.PatientEntityRepository;
import org.emedical.service.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientEntityRepository repository;
    private final ModelMapper modelMapper;

    public List<Patient> getAllPatients(){
        return repository.findAll().stream().map(p -> modelMapper.map(p, Patient.class))
                .collect(Collectors.toList());
    }

    @Override
    public Patient findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Patient.class);
    }

    @Override
    public Patient savePatient(Patient patient) {

        PatientEntity patientEntity = PatientMapper.toEntity(patient);

        PatientEntity savedEntity = repository.save(patientEntity);
        return PatientMapper.toDto(savedEntity);
    }

    @Override
    public Patient editPatient(Patient patient) {

        PatientEntity patientEntity = repository.findById(patient.getId())
        .orElseThrow(() -> new IllegalArgumentException("This patient does not exist!"));

        PatientMapper.updateEntity(patientEntity, patient);
        PatientEntity updated = repository.save(patientEntity);

        return PatientMapper.toDto(updated);
    }

}
