package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;

public interface MedicalRecordService {
    MedicalRecord createMedicalRecord(MedicalRecordRequest request, String doctorUsername) throws NotFoundException;
}
