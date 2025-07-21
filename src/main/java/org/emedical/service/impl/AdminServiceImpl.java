package org.emedical.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;
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

    //TODO kreiranje tima doktora
}
