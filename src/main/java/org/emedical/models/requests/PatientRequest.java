package org.emedical.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PatientRequest {
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;

    @NotBlank
    private String jmb;

    @NotBlank
    private String pioNumber;

    private Integer teamId;
}
