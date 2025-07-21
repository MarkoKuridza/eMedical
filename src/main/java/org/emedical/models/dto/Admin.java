package org.emedical.models.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Admin extends User {
    private String first_name;
    private String last_name;
}
