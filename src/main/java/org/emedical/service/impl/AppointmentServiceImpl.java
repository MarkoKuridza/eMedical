package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.Appointment;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.service.AppointentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.emedical.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointentService {

    private final AppointmentEntityRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public List<Appointment> findAll() {
        return repository.findAll().stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment findbyId(Integer id) throws NotFoundException {
        return modelMapper.map(repository.findById(id).orElseThrow(NotFoundException::new), Appointment.class);
    }

    @Override
    public List<Appointment> getAllAppointmentsByDoctorId(Integer id) {
        return repository.getAllByDoctor_Id(id).stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }
}
