package org.emedical.models.dto;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String firstName;
    private String lastName;
    private String jmb;
    private String pioNumber; 
    private Integer teamId;
}
