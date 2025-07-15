package org.emedical.models.dto;

import lombok.Data;
import org.emedical.models.enums.Status;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Integer id;
    private LocalDateTime appointmentDate;
    private Status appointmentStatus;
    private Patient patient;
}
