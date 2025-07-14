package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Basic
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Basic
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments;
}
