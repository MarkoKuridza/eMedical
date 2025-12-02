package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "team")
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false)
    private Integer teamId;

    @Basic
    @Column(name = "name", nullable = false)
    private String teamName;

    @OneToOne(mappedBy = "team")
    private DoctorEntity doctor;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<NurseEntity> nurses;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments;

}
