package org.emedical.models.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Integer doctorId;
    private Integer nurseId = null;
    private Integer patientId;
    private Integer teamId = null;
    private LocalDateTime appointmentDate;
    private String appointmentDetails;
}
