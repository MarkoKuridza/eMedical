package org.emedical.models.requests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.emedical.models.enums.Status;

import java.time.LocalDateTime;

@Data
public class AppointmentRequest {
    private Integer doctorId;
    private Integer nurseId = null;
    @NotNull
    private Integer patientId;
    private Integer teamId = null;
    @FutureOrPresent
    private LocalDateTime appointmentDate;
    @NotBlank
    private String appointmentDetails;
    private Status appointmentStatus;
}
