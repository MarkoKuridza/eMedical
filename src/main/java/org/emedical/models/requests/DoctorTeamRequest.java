package org.emedical.models.requests;

import java.util.Set;

import lombok.Data;

@Data
public class DoctorTeamRequest {
    
    private Integer doctorId;
    private Set<Integer> nurseIds;
    private Set<Integer> patientIds;
}
