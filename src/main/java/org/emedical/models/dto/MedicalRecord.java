package org.emedical.models.dto;

import lombok.Data;

@Data
public class MedicalRecord {
    private Integer patientId;
    private Integer doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private String createdAt;
    private Integer appointmentId;
    private String diagnosis;
    private String prescription;
    private Boolean emergency;
    private Boolean hasPatientArrived;
}
