package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.enums.Role;
import org.emedical.models.mappers.DoctorMapper;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorEntityRepository repository;
    private final ModelMapper modelMapper;

    public List<Doctor> getAllDoctors() {
        return repository.findAll().stream().map(n -> modelMapper.map(n, Doctor.class)).collect(Collectors.toList());
    }

    @Override
    public Doctor findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Doctor.class);
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {

        DoctorEntity doctorEntity = DoctorMapper.toEntity(doctor);
        doctorEntity.setRole(Role.DOCTOR);
        doctorEntity.setIsActive(true);

        DoctorEntity savedEntity = repository.save(doctorEntity);
        return DoctorMapper.toDto(savedEntity);
    }

    @Override
    public Doctor editDoctor(Doctor nurse) {

        DoctorEntity doctorEntity = repository.findById(nurse.getId())
        .orElseThrow(() -> new IllegalArgumentException("This doctor does not exist!"));

        DoctorMapper.updateEntity(doctorEntity, nurse);
        DoctorEntity updated = repository.save(doctorEntity);

        return DoctorMapper.toDto(updated);
    }

    @Override
    public void setActive(Integer id) {
        DoctorEntity doctorEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This doctor does not exist!"));

        doctorEntity.setIsActive(true);
        repository.save(doctorEntity);
    }

    @Override
    public void setInactive(Integer id) {
        DoctorEntity doctorEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This doctor does not exist!"));

        doctorEntity.setIsActive(false);
        repository.save(doctorEntity);
    }
}
