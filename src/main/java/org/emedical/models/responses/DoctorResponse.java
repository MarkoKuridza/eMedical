package org.emedical.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DoctorResponse {
    
    private String firstName;
    private String lastName;
}
