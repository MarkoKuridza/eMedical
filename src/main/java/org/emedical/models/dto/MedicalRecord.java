package org.emedical.models.dto;

import lombok.Data;

@Data
public class MedicalRecord {
    private Integer patientId;
    private Integer appointmentId;
    private String diagnosis;
    private String prescription;
}
