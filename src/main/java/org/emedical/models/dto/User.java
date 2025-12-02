package org.emedical.models.dto;

import lombok.Data;
import org.emedical.models.enums.Role;

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private Role role;
    private Boolean isActive;
}
