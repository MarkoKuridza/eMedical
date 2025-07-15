package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.entities.MedicalRecordEntity;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.repositories.MedicalRecordEntityRepository;
import org.emedical.service.MedicalRecordService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordEntityRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecordRequest request, String doctorUsername) {
        MedicalRecordEntity medicalRecord = modelMapper.map(request, MedicalRecordEntity.class);
        medicalRecord.setId(null); //id je autoinkrement pa da se ne bi slucajno preko modelmappera unio
        medicalRecord.setCreatedAt(LocalDateTime.now());
        medicalRecord = repository.saveAndFlush(medicalRecord);

        return modelMapper.map(repository.findById(medicalRecord.getId()), MedicalRecord.class);
    }
}
