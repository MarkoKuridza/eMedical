package org.emedical.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Nurse;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.enums.Role;
import org.emedical.models.mappers.NurseMapper;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.service.NurseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NurseServiceImpl implements NurseService {
    
    private final NurseEntityRepository repository;
    private final ModelMapper modelMapper;

    public List<Nurse> getAllNurses() {
        return repository.findAll().stream().map(n -> modelMapper.map(n, Nurse.class)).collect(Collectors.toList());
    }

    @Override
    public Nurse findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Nurse.class);
    }

    @Override
    public Nurse createNurse(Nurse nurse) {

        NurseEntity nurseEntity = NurseMapper.toEntity(nurse);
        nurseEntity.setRole(Role.NURSE);
        nurseEntity.setIsActive(true);

        NurseEntity savedEntity = repository.save(nurseEntity);
        return NurseMapper.toDto(savedEntity);
    }

    @Override
    public Nurse editNurse(Nurse nurse) {

        NurseEntity nurseEntity = repository.findById(nurse.getId())
        .orElseThrow(() -> new IllegalArgumentException("This nurse does not exist!"));

        NurseMapper.updateEntity(nurseEntity, nurse);
        NurseEntity updated = repository.save(nurseEntity);

        return NurseMapper.toDto(updated);
    }

    @Override
    public void setActive(Integer id) {
        NurseEntity nurseEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This nurse does not exist!"));

        nurseEntity.setIsActive(true);
        repository.save(nurseEntity);
    }

    @Override
    public void setInactive(Integer id) {
        NurseEntity nurseEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This nurse does not exist!"));

        nurseEntity.setIsActive(false);
        repository.save(nurseEntity);
    }
}
