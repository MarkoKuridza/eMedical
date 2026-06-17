package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.security.CustomUserDetails;

import java.util.List;

public interface MedicalRecordService {
    MedicalRecord finishAppointment(Integer appointmentId,
                                    MedicalRecordRequest request,
                                    CustomUserDetails doctor) throws NotFoundException;

    MedicalRecord getMedicalRecordByAppointmentId(Integer appointmentId) throws NotFoundException;

    List<MedicalRecord> getAllMedicalRecordsByPatientId(Integer id, CustomUserDetails user);
}
