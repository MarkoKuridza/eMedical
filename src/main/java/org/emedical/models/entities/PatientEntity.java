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
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic
    @Column(name = "jmb", nullable = false, unique = true)
    private String jmb;

    @Basic
    @Column(name = "pio_number", nullable = false, unique = true)
    private String pioNumber;

    @OneToMany(mappedBy = "patient")
    private List<AppointmentEntity> appointments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private TeamEntity team;

    @OneToMany(mappedBy = "patient")
    private List<MedicalRecordEntity> medicalRecords;
}
