package org.emedical.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NurseRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String jmb;
    private Integer teamId;
}
