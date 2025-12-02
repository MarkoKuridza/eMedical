package org.emedical.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class Team {
    private Integer teamId;
    private Integer teamName;
    private Doctor doctorId;
    private List<Integer> nursesId;
}
