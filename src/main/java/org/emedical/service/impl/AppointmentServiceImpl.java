package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.entities.*;
import org.emedical.models.enums.Status;
import org.emedical.models.enums.WaitingRoomStatus;
import org.emedical.models.requests.AppointmentRequest;
import org.emedical.repositories.*;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AppointmentService;
import org.emedical.service.WebSocketNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.emedical.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentEntityRepository appointmentRepository;

    //treba sve prebaciti u servise da se ne koriste direktno repozitorijumi
    //al o tom po tom
    private final DoctorEntityRepository doctorRepository;
    private final NurseEntityRepository nurseRepository;
    private final PatientEntityRepository patientRepository;
    private final WaitingRoomEntityRepository waitingRoomRepository;
    private final MedicalRecordEntityRepository medicalRecordRepository;

    private final ModelMapper modelMapper;
    private final WebSocketNotificationService wsNotificationService;

    @Override
    public Appointment createAppointment(AppointmentRequest request, CustomUserDetails user) throws NotFoundException {
        NurseEntity nurse = nurseRepository.findNurseEntitiesByIdAndTeam_TeamId(user.getId(), user.getTeamId())
                .orElseThrow(() -> new NotFoundException("Nurse not found"));
        DoctorEntity doctor = doctorRepository.getDoctorEntityByTeam_TeamId(user.getTeamId())
                .orElseThrow(() -> new BadRequestException("Doctor must belong to the same team as the nurse."));
        PatientEntity patient = patientRepository.findById(request.getPatientId()).orElseThrow(() -> new NotFoundException("Patient not found"));

        AppointmentEntity appointment = new AppointmentEntity();
        if (request.getAppointmentStatus() == Status.EMERGENCY) {
            appointment.setAppointmentDate(LocalDateTime.now());
            appointment.setAppointmentStatus(Status.EMERGENCY);
        } else {
            if (request.getAppointmentDate() == null) {
                throw new BadRequestException("Appointment date is required.");
            }
            appointment.setAppointmentDate(request.getAppointmentDate());
            appointment.setAppointmentStatus(Status.SCHEDULED);
        }

        ensureDoctorIsAvailable(doctor.getId(), appointment.getAppointmentDate(), null);

        appointment.setAppointmentDetails(request.getAppointmentDetails());
        appointment.setDoctor(doctor);
        appointment.setNurse(nurse);
        appointment.setPatient(patient);
        appointment.setTeam(nurse.getTeam());

        appointmentRepository.save(appointment);

        return modelMapper.map(appointment, Appointment.class);
    }

    @Override
    public List<Appointment> getAllAppointmentsByTeamId(Integer id) {
        return appointmentRepository.findAppointmentEntitiesByTeam_TeamIdOrderByAppointmentDateAsc(id)
                .stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Appointment> getAppointmentsPendingCompletion(Integer teamId) {
        return appointmentRepository.findAppointmentEntitiesByTeam_TeamIdAndAppointmentStatusOrderByAppointmentDateAsc(teamId, Status.PENDING_NURSE_COMPLETION)
                .stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment markPatientArrived(Integer id, CustomUserDetails user) throws NotFoundException {
        AppointmentEntity appointment = getAppointmentForTeam(id, user.getTeamId());
        NurseEntity nurse = nurseRepository.findNurseEntitiesByIdAndTeam_TeamId(user.getId(), user.getTeamId())
                .orElseThrow(() -> new NotFoundException("Nurse not found"));

        if (appointment.getAppointmentStatus() == Status.CANCELED) {
            throw new BadRequestException("Canceled appointments can't be added to waiting room");
        }

        if (appointment.getAppointmentStatus() == Status.COMPLETED || appointment.getAppointmentStatus() == Status.PENDING_NURSE_COMPLETION) {
            throw new BadRequestException("Appointment has already been processed");
        }

        WaitingRoomEntity waitingRoom = waitingRoomRepository.findWaitingRoomEntitiesByAppointment_Id(id)
                .orElse(null);
        if (waitingRoom != null && waitingRoom.getStatus() != WaitingRoomStatus.COMPLETED) {
            throw new BadRequestException("Patient is already checked into the waiting room");
        }

        LocalDateTime now = LocalDateTime.now();
        if (waitingRoom == null) {
            waitingRoom = new WaitingRoomEntity();
            waitingRoom.setQueueNumber(waitingRoomRepository.findTopByTeam_TeamIdOrderByQueueNumberDesc(user.getTeamId())
                    .map(entry -> entry.getQueueNumber() + 1)
                    .orElse(1));
            waitingRoom.setArrivedAt(now);
        }

        waitingRoom.setAppointment(appointment);
        waitingRoom.setTeam(appointment.getTeam());
        waitingRoom.setDoctor(appointment.getDoctor());
        waitingRoom.setNurse(nurse);
        waitingRoom.setPatient(appointment.getPatient());
        waitingRoom.setDoctorNotifiedAt(now);
        waitingRoom.setDoctorFinishedAt(null);
        waitingRoom.setCompletedAt(null);
        waitingRoom.setStatus(WaitingRoomStatus.WAITING_FOR_DOCTOR);

        appointment.setNurse(nurse);
        appointment.setAppointmentStatus(Status.WAITING);
        appointment.setWaitingRoomEntry(waitingRoomRepository.save(waitingRoom));

        appointmentRepository.save(appointment);

        WaitingRoom waitingRoomDto = modelMapper.map(waitingRoom, WaitingRoom.class);
        wsNotificationService.notifyDoctorPatientArrived(appointment.getDoctor().getId(), waitingRoomDto);

        return modelMapper.map(appointment, Appointment.class);
    }

    @Override
    public Appointment completeAppointment(Integer id, CustomUserDetails user) throws NotFoundException {
        AppointmentEntity appointment = getAppointmentForTeam(id, user.getTeamId());
        NurseEntity nurse = nurseRepository.findNurseEntitiesByIdAndTeam_TeamId(user.getId(), user.getTeamId())
                .orElseThrow(() -> new NotFoundException("Nurse not found"));
        MedicalRecordEntity medicalRecord = medicalRecordRepository.findMedicalRecordEntitiesByAppointment_Id(id)
                .orElseThrow(() -> new BadRequestException("Doctor must finish the appointment before a nurse can complete it."));
        WaitingRoomEntity waitingRoom = waitingRoomRepository.findWaitingRoomEntitiesByAppointment_Id(id)
                .orElseThrow(() -> new BadRequestException("Appointment is not in the waiting room workflow."));

        if (appointment.getAppointmentStatus() != Status.PENDING_NURSE_COMPLETION) {
            throw new BadRequestException("Only appointments pending nurse completion can be completed.");
        }

        if (waitingRoom.getStatus() != WaitingRoomStatus.READY_FOR_NURSE) {
            throw new BadRequestException("Doctor has not finished processing this patient yet.");
        }

        appointment.setAppointmentStatus(Status.COMPLETED);
        appointment.setNurse(nurse);
        appointment.setMedicalRecord(medicalRecord);

        waitingRoom.setNurse(nurse);
        waitingRoom.setStatus(WaitingRoomStatus.COMPLETED);
        waitingRoom.setCompletedAt(LocalDateTime.now());
        appointment.setWaitingRoomEntry(waitingRoomRepository.save(waitingRoom));

        appointmentRepository.save(appointment);

        WaitingRoom waitingRoomDto = modelMapper.map(waitingRoom, WaitingRoom.class);
        wsNotificationService.notifyTeamAppointmentCompleted(appointment.getTeam().getTeamId(), waitingRoomDto);

        return modelMapper.map(appointment, Appointment.class);
    }


    @Override
    public Appointment updateAppointment(Integer id, Appointment updatedAppointment, CustomUserDetails user) throws NotFoundException {
        AppointmentEntity currentAppointment = getAppointmentForTeam(id, user.getTeamId());
        ensureAppointmentCanBeModified(currentAppointment);

        if (updatedAppointment.getPatientId() != null &&
                (currentAppointment.getPatient() == null || !updatedAppointment.getPatientId().equals(currentAppointment.getPatient().getId()))) {
            PatientEntity patient = patientRepository.findById(updatedAppointment.getPatientId()).orElseThrow(() -> new NotFoundException("Patient not found"));
            patient.setTeam(currentAppointment.getTeam());
            currentAppointment.setPatient(patient);
        }

        if (updatedAppointment.getAppointmentDate() != null) {
            ensureDoctorIsAvailable(currentAppointment.getDoctor().getId(), updatedAppointment.getAppointmentDate(), currentAppointment.getId());
            currentAppointment.setAppointmentDate(updatedAppointment.getAppointmentDate());
        }

        if (updatedAppointment.getAppointmentDetails() != null && !updatedAppointment.getAppointmentDetails().isBlank()) {
            currentAppointment.setAppointmentDetails(updatedAppointment.getAppointmentDetails());
        }

        if (updatedAppointment.getAppointmentStatus() != null) {
            if (updatedAppointment.getAppointmentStatus() != Status.SCHEDULED && updatedAppointment.getAppointmentStatus() != Status.CANCELED) {
                throw new BadRequestException("Only SCHEDULED or CANCELED can be set manually.");
            }
            currentAppointment.setAppointmentStatus(updatedAppointment.getAppointmentStatus());
        }

        appointmentRepository.save(currentAppointment);

        return modelMapper.map(currentAppointment, Appointment.class);
    }

    @Override
    public void deleteAppointment(Integer id, CustomUserDetails user) throws NotFoundException {
        AppointmentEntity appointment = getAppointmentForTeam(id, user.getTeamId());
        if (appointment.getAppointmentStatus() != Status.SCHEDULED && appointment.getAppointmentStatus() != Status.CANCELED) {
            throw new BadRequestException("Only scheduled or canceled appointments can be deleted.");
        }

        if (medicalRecordRepository.findMedicalRecordEntitiesByAppointment_Id(id).isPresent()) {
            throw new BadRequestException("Appointments with a medical record cannot be deleted.");
        }

        WaitingRoomEntity waitingRoom = waitingRoomRepository.findWaitingRoomEntitiesByAppointment_Id(id).orElse(null);
        if (waitingRoom != null && waitingRoom.getStatus() != WaitingRoomStatus.COMPLETED) {
            throw new BadRequestException("Remove the patient from the active workflow before deleting the appointment.");
        }

        appointmentRepository.delete(appointment);
    }

    @Override
    public List<Appointment> getDoctorAppointments(Integer doctorId) {
        return appointmentRepository
                .findAppointmentEntitiesByDoctor_IdOrderByAppointmentDateAsc(doctorId)
                .stream().map(a -> modelMapper.map(a, Appointment.class))
                .collect(Collectors.toList());
    }

    @Override
    public Appointment startAppointment(Integer appointmentId, CustomUserDetails doctor) throws NotFoundException {
        AppointmentEntity appointment = getAppointmentForTeam(appointmentId, doctor.getTeamId());

        if (!appointment.getDoctor().getId().equals(doctor.getId()))
            throw new BadRequestException("You can only start your own appointments.");

        if (appointment.getAppointmentStatus() != Status.WAITING)
            throw new BadRequestException(
                    "Appointment can only be started when status is WAITING. Current status: "
                            + appointment.getAppointmentStatus());

        appointment.setAppointmentStatus(Status.IN_PROGRESS);
        appointmentRepository.save(appointment);
        return modelMapper.map(appointment, Appointment.class);
    }

    private void ensureAppointmentCanBeModified(AppointmentEntity appointment) {
        if (appointment.getAppointmentStatus() != Status.SCHEDULED && appointment.getAppointmentStatus() != Status.CANCELED) {
            throw new BadRequestException("Only scheduled or canceled appointments can be updated.");
        }

        if (medicalRecordRepository.findMedicalRecordEntitiesByAppointment_Id(appointment.getId()).isPresent()) {
            throw new BadRequestException("Appointments with a medical record can no longer be changed.");
        }

        WaitingRoomEntity waitingRoom = waitingRoomRepository.findWaitingRoomEntitiesByAppointment_Id(appointment.getId()).orElse(null);
        if (waitingRoom != null && waitingRoom.getStatus() != WaitingRoomStatus.COMPLETED) {
            throw new BadRequestException("Appointments in the active waiting room cannot be updated.");
        }
    }

    private AppointmentEntity getAppointmentForTeam(Integer appointmentId, Integer teamId) throws NotFoundException {
        return appointmentRepository.findAppointmentEntitiesByIdAndTeam_TeamId(appointmentId, teamId)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));
    }

    private void ensureDoctorIsAvailable(Integer doctorId, LocalDateTime appointmentDate, Integer currentAppointmentId) {
        boolean hasConflict = appointmentRepository
                .findAppointmentEntitiesByDoctor_IdAndAppointmentDateAndAppointmentStatusNot(doctorId, appointmentDate, Status.CANCELED)
                .stream()
                .anyMatch(appointment -> !appointment.getId().equals(currentAppointmentId));

        if (hasConflict) {
            throw new BadRequestException("Doctor already has an appointment at this time.");
        }
    }
}
