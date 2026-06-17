package org.emedical.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MedicalRecordRequest {
    @NotBlank
    private String diagnosis;
    private String prescription;
    private String referral;
    private Boolean emergency;
    private Integer appointmentId;
}
