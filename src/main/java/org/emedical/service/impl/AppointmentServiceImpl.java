package org.emedical.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.Appointment;
import org.emedical.models.entities.AppointmentEntity;
import org.emedical.models.enums.Status;
import org.emedical.models.requests.AppointmentRequest;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.emedical.exceptions.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentEntityRepository appointmentRepository;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Appointment findById(Integer id) throws NotFoundException {
        return modelMapper.map(appointmentRepository.findById(id).orElseThrow(NotFoundException::new), Appointment.class);
    }

    @Override
    public List<Appointment> getAllAppointmentsByTeamId(Integer id){
        return appointmentRepository.getAllByTeam_TeamId(id).stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment updateAppointment(Integer id, Appointment updatedAppointment) throws NotFoundException {
        AppointmentEntity currentAppointment = appointmentRepository.findById(id).orElseThrow(NotFoundException::new);

        currentAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        currentAppointment.setAppointmentStatus(updatedAppointment.getAppointmentStatus());
        currentAppointment.setAppointmentDetails(updatedAppointment.getAppointmentDetails());

        appointmentRepository.saveAndFlush(currentAppointment);
        entityManager.persist(currentAppointment);
        entityManager.refresh(currentAppointment);

        return modelMapper.map(currentAppointment, Appointment.class);
    }

    @Override
    public void deleteAppointment(Integer id) throws NotFoundException {
        if(!appointmentRepository.existsById(id)){
            throw new NotFoundException();
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public Appointment createAppointment(AppointmentRequest request, CustomUserDetails user) {
        request.setNurseId(user.getId());
        request.setTeamId(user.getTeamId());
        AppointmentEntity appointment = modelMapper.map(request, AppointmentEntity.class);
        appointment.setId(null);

        //appointment.setAppointmentStatus(Status.SCHEDULED);

        appointment = appointmentRepository.saveAndFlush(appointment);
        entityManager.persist(appointment);

        entityManager.refresh(appointment);

        return modelMapper.map(appointmentRepository.findById(appointment.getId()), Appointment.class);
    }


}
