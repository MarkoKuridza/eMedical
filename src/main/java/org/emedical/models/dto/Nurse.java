package org.emedical.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nurse {
    private Integer id;
    private String username;
    private String firstName;
    private String lastName;
    private String jmb;
    private Integer teamId;
}
