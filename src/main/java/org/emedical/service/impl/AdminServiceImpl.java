package org.emedical.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;
import org.emedical.models.entities.AdminEntity;
import org.emedical.models.enums.Role;
import org.emedical.models.mappers.AdminMapper;
import org.emedical.repositories.AdminEntityRepository;
import org.emedical.service.AdminService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminEntityRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public List<Admin> findAll() {
        return repository.findAll().stream().map(a -> modelMapper.map(a, Admin.class)).collect(Collectors.toList());
    }

    @Override
    public Admin findById(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Admin.class);
    }

    @Override
    public Admin saveAdmin(Admin admin) {

        AdminEntity adminEntity = AdminMapper.toEntity(admin);
        adminEntity.setRole(Role.ADMIN);
        adminEntity.setIsActive(true);

        AdminEntity savedEntity = repository.save(adminEntity);
        return AdminMapper.toDto(savedEntity);
    }

    @Override
    public Admin editAdmin(Admin admin) {

        AdminEntity adminEntity = repository.findById(admin.getId())
        .orElseThrow(() -> new IllegalArgumentException("This admin does not exist!"));

        AdminMapper.updateEntity(adminEntity, admin);
        AdminEntity updated = repository.save(adminEntity);

        return AdminMapper.toDto(updated);
    }

    @Override
    public void setActive(Integer id) {
        AdminEntity adminEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This admin does not exist!"));

        adminEntity.setIsActive(true);
        repository.save(adminEntity);
    }

    @Override
    public void setInactive(Integer id) {
        AdminEntity adminEntity = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("This admin does not exist!"));

        adminEntity.setIsActive(false);
        repository.save(adminEntity);
    }
}
