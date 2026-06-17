package org.emedical.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private Integer teamId;
    private String teamName;
    private Integer doctorId;
    private List<Integer> nursesId;
}
