package org.emedical.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Doctor extends User {
    private String first_name;
    private String last_name;
    private String specialization;
    private List<Appointment> appointments;
    private Set<Patient> waitingPatients;
}
