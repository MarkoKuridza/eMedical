package org.emedical.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.emedical.models.entities.AppointmentEntity;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class Doctor extends User {
    private String first_name;
    private String last_name;
    private String specialization;
    private List<AppointmentEntity> appointments;
}
