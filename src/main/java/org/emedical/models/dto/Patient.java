package org.emedical.models.dto;

import lombok.Data;

@Data
public class Patient {
    private Integer id;
    private String first_name;
    private String last_name;
    private Integer doctor_id;
}
