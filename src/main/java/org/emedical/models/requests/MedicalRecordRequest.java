package org.emedical.models.requests;

import lombok.Data;

@Data
public class MedicalRecordRequest {
    private String diagnosis;
    private String prescription;
    private Integer appointmentId;
    private Integer doctorId;
    private Integer patientId;
}
