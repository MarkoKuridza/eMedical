package org.emedical.service;

import jakarta.validation.Valid;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.Doctor;
import org.emedical.models.requests.DoctorRequest;
import org.emedical.models.responses.DoctorResponse;
import org.emedical.security.CustomUserDetails;

import java.util.List;

public interface DoctorService {

    DoctorResponse getDoctorByTeamId(Integer teamId);
    List<Doctor> getAllDoctors();

//    List<WaitingRoom> getDoctorQueue(Integer doctorId);

    List<Appointment> getDoctorAppointments(Integer teamId);

    Appointment startAppointment(Integer appointmentId, CustomUserDetails doctor) throws NotFoundException;

    Doctor registerDoctor(DoctorRequest request);

    Doctor updateDoctor(Integer id, @Valid DoctorRequest request) throws NotFoundException;

    void deleteDoctor(Integer id) throws NotFoundException;
}
