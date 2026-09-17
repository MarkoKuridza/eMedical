package org.emedical.models.dto;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String firstName;
    private String lastName;
    private String jmb;
    private String pioNumber; 
    private String phoneNumber;
    private String address;
    private String doctorNotice;
    private Integer teamId;
}
