package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.Patient;
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
}
