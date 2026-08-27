package org.emedical.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.emedical.models.enums.Status;

import java.time.LocalDateTime;

@Data
public class Appointment {
    private Integer id;
    private Integer doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    private Integer nurseId;
    private String nurseFirstName;
    private String nurseLastName;
    private Integer patientId;
    private String patientJmb;
    private String patientPio;
    private String patientFirstName;
    private String patientLastName;
    private Integer teamId;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime appointmentDate;
    private String appointmentDetails;
    private Status appointmentStatus;
    private Integer queueNumber;
    private Boolean doctorNotified;
    private Boolean readyForNurseCompletion;
    private Integer medicalRecordId;
}
