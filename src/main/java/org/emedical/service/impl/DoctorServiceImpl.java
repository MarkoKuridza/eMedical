package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorEntityRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public Doctor getDoctorByTeamId(Integer teamId) {
        return modelMapper.map(repository.getDoctorEntityByTeam_TeamId(teamId), Doctor.class);
    }
}
