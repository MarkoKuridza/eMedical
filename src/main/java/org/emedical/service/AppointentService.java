package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;

import java.util.List;

public interface AppointentService {
    List<Appointment> findAll();
    Appointment findbyId(Integer id) throws NotFoundException;

    List<Appointment> getAllAppointmentsByDoctorId(Integer id);
}
