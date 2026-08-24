package org.emedical.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

//Ne da mi se ovo ispravljati
//
@Data
public class MedicalRecord {
    private Integer id;
    private Integer patientId;
    private String patientFirstName;
    private String patientLastName;
    private Integer doctorId;
    private String doctorFirstName;
    private String doctorLastName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private Integer appointmentId;
    private String diagnosis;
    private String prescription;
    private String referral;
    private Boolean emergency;
}
