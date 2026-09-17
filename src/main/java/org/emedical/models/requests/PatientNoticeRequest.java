package org.emedical.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data 
public class PatientNoticeRequest {
    
    @NotBlank 
    private String doctorNotice;
}
