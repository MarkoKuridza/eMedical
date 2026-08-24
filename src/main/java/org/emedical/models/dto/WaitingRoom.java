package org.emedical.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.emedical.models.enums.Status;
import org.emedical.models.enums.WaitingRoomStatus;

import java.time.LocalDateTime;

//Ne da mi se ovo ispravljati
//
@Data
public class WaitingRoom {
    private Integer id;
    private Integer appointmentId;
    private Integer queueNumber;
    private Integer teamId;
    private Integer doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private Integer nurseId;
    private String nurseFirstName;
    private String nurseLastName;
    private Integer patientId;
    private String patientFirstName;
    private String patientLastName;
    private WaitingRoomStatus status;
    private Status appointmentStatus;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime doctorNotifiedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime doctorFinishedAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime completedAt;
}
