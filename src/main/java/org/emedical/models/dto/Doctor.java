package org.emedical.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class Doctor extends User {
    private String firstName;
    private String lastName;
    private String jmb;
    private Integer teamId;
    private String specialization;
    private List<Appointment> appointments;
    private Set<Patient> waitingPatients;
}


//Prosiri appointments da ima vise informacija o pacijentu i dodati napomenu kod doktora kada pregleda paccijenta 
//isto mob i mail od pacijenta i vazne dijagnoze