package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.requests.AppointmentRequest;
import org.emedical.security.CustomUserDetails;

import java.util.List;

public interface AppointmentService {
    Appointment findById(Integer id) throws NotFoundException;
    Appointment createAppointment(AppointmentRequest request, CustomUserDetails user);
    List<Appointment> getAllAppointmentsByTeamId(Integer id);

    Appointment updateAppointment(Integer id, Appointment updatedAppointment) throws NotFoundException;

    void deleteAppointment(Integer id) throws NotFoundException;
}
