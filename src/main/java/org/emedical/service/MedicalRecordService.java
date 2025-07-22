package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord createMedicalRecord(MedicalRecordRequest request, String doctorUsername) throws NotFoundException;

    List<MedicalRecord> getAllMedicalRecordsByPatientId(Integer id);
}
