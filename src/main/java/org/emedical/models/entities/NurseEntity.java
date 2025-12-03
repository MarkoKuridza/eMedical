package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "nurse")
public class NurseEntity extends UserEntity {

    @Basic
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Basic
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;
}
