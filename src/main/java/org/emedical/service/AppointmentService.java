package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.requests.AppointmentRequest;
import org.emedical.security.CustomUserDetails;

import java.util.List;

public interface AppointmentService {
    Appointment createAppointment(AppointmentRequest request, CustomUserDetails user) throws NotFoundException;

    List<Appointment> getAllAppointmentsByTeamId(Integer id);

    List<Appointment> getAppointmentsPendingCompletion(Integer teamId);

    Appointment markPatientArrived(Integer id, CustomUserDetails user) throws NotFoundException;

    Appointment completeAppointment(Integer id, CustomUserDetails user) throws NotFoundException;

    Appointment updateAppointment(Integer id, Appointment updatedAppointment, CustomUserDetails user) throws NotFoundException;

    void deleteAppointment(Integer id, CustomUserDetails user) throws NotFoundException;

    List<Appointment> getDoctorAppointments(Integer doctorId);

    Appointment startAppointment(Integer appointmentId, CustomUserDetails doctor) throws NotFoundException;
}
