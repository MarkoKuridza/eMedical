package org.emedical.service.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.entities.AppointmentEntity;
import org.emedical.models.entities.MedicalRecordEntity;
import org.emedical.models.enums.Status;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.repositories.MedicalRecordEntityRepository;
import org.emedical.service.AppointmentService;
import org.emedical.service.MedicalRecordService;
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
    private final AppointmentService appointentService;
    private final ModelMapper modelMapper;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordRequest request, String doctorUsername) throws NotFoundException {
        MedicalRecordEntity medicalRecord = modelMapper.map(request, MedicalRecordEntity.class);
        medicalRecord.setId(null); //id je autoinkrement pa da se ne bi slucajno preko modelmappera unio
        medicalRecord.setCreatedAt(LocalDateTime.now());

        if(request.getAppointmentId() != null) {
            Appointment appointment = appointentService.findById(request.getAppointmentId());

            AppointmentEntity appointmentEntity = modelMapper.map(appointment, AppointmentEntity.class);
            appointmentEntity.setAppointmentStatus(Status.COMPLETED);
            appointmentEntity = appointmentRepository.saveAndFlush(appointmentEntity);
            entityManager.refresh(appointmentEntity);
        }

        medicalRecord = medicalRepository.saveAndFlush(medicalRecord);
        entityManager.refresh(medicalRecord);

        return modelMapper.map(medicalRepository.findById(medicalRecord.getId()), MedicalRecord.class);
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecordsByPatientId(Integer id) {
        return medicalRepository.getAllByPatient_Id(id).stream().map(mr -> modelMapper.map(mr, MedicalRecord.class))
                .collect(Collectors.toList());
    }
}
