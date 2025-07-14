package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.DoctorService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorEntityRepository repository;

}
