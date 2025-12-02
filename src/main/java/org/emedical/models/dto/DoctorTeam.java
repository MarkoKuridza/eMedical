package org.emedical.models.dto;

import java.util.Set;

import lombok.Data;

@Data
public class DoctorTeam {
    
    private Integer id;
    private Doctor doctor;
    private Set<Nurse> nurses;
    private Set<Patient> patients;
}
