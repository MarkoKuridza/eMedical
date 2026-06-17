package org.emedical.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.entities.AppointmentEntity;
import org.emedical.models.entities.MedicalRecordEntity;
import org.emedical.models.entities.WaitingRoomEntity;
import org.emedical.models.enums.Status;
import org.emedical.models.enums.WaitingRoomStatus;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.repositories.MedicalRecordEntityRepository;
import org.emedical.repositories.WaitingRoomEntityRepository;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.MedicalRecordService;
import org.emedical.service.WebSocketNotificationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordEntityRepository medicalRepository;
    private final AppointmentEntityRepository appointmentRepository;
    private final WaitingRoomEntityRepository waitingRoomRepository;
    private final WebSocketNotificationService wsNotificationService;
    private final ModelMapper modelMapper;

    @Override
    public MedicalRecord finishAppointment(Integer appointmentId,
                                           MedicalRecordRequest request,
                                           CustomUserDetails doctor) throws NotFoundException {
        AppointmentEntity appointment = appointmentRepository.findAppointmentEntitiesByIdAndTeam_TeamId(appointmentId, doctor.getTeamId())
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        if (appointment.getAppointmentStatus() != Status.WAITING && appointment.getAppointmentStatus() != Status.IN_PROGRESS) {
            throw new BadRequestException("Only waiting or active appointments can be finished by a doctor.");
        }

        WaitingRoomEntity waitingRoom = waitingRoomRepository.findWaitingRoomEntitiesByAppointment_Id(appointmentId)
                .orElseThrow(() -> new BadRequestException("Patient must be in the waiting room before processing."));

        if (waitingRoom.getStatus() != WaitingRoomStatus.WAITING_FOR_DOCTOR) {
            throw new BadRequestException("This patient has already been handed off from the doctor.");
        }

        //ovo sam mogao i preko modelMappera...
        MedicalRecordEntity medicalRecord = medicalRepository.findMedicalRecordEntitiesByAppointment_Id(appointmentId).orElseGet(MedicalRecordEntity::new);
        medicalRecord.setAppointment(appointment);
        medicalRecord.setDoctor(appointment.getDoctor());
        medicalRecord.setPatient(appointment.getPatient());
        medicalRecord.setDiagnosis(request.getDiagnosis());
        medicalRecord.setPrescription(blankToNull(request.getPrescription()));
        medicalRecord.setReferral(blankToNull(request.getReferral()));
        medicalRecord.setEmergency(Boolean.TRUE.equals(request.getEmergency()));
        medicalRecord.setPatientFirstName(appointment.getPatient().getFirstName());
        medicalRecord.setPatientLastName(appointment.getPatient().getLastName());
        medicalRecord.setUpdatedAt(LocalDateTime.now());

        medicalRecord = medicalRepository.save(medicalRecord);

        appointment.setMedicalRecord(medicalRecord);
        appointment.setAppointmentStatus(Status.PENDING_NURSE_COMPLETION);
        appointmentRepository.save(appointment);

        waitingRoom.setStatus(WaitingRoomStatus.READY_FOR_NURSE);
        waitingRoom.setDoctorFinishedAt(LocalDateTime.now());
        waitingRoomRepository.save(waitingRoom);

        WaitingRoom waitingRoomDto = modelMapper.map(waitingRoom, WaitingRoom.class);
        wsNotificationService.notifyNursePatientReady(appointment.getTeam().getTeamId(), waitingRoomDto);

        return modelMapper.map(medicalRecord, MedicalRecord.class);
    }

    @Override
    public MedicalRecord getMedicalRecordByAppointmentId(Integer appointmentId) throws NotFoundException {
        return modelMapper.map(medicalRepository.findMedicalRecordEntitiesByAppointment_Id(appointmentId)
                .orElseThrow(() -> new NotFoundException("Medical record not found")), MedicalRecord.class);
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecordsByPatientId(Integer id, CustomUserDetails user) {
        return medicalRepository.getMedicalRecordEntitiesByPatient_IdAndPatient_Team_TeamIdOrderByCreatedAtDesc(id, user.getTeamId())
                .stream()
                .map(mr -> modelMapper.map(mr, MedicalRecord.class))
                .collect(Collectors.toList());
    }

    private String blankToNull(String value) {
        return value == null || value.isBlank() ? null : value;
    }

}
