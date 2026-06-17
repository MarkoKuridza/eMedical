package org.emedical.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WaitingRoomMessage {
    public enum Type {
        PATIENT_ARRIVED,
        DOCTOR_FINISHED,
        APPOINTMENT_COMPLETED
    }

    private Type type;
    private WaitingRoom payload;
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime timestamp;
}
