package org.emedical.models.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "doctor_team")
public class DoctorTeamEntity {
    
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
        name = "doctor_team_nurses",
        joinColumns = @JoinColumn(name = "team_id"),
        inverseJoinColumns = @JoinColumn(name = "nurse_id")
    )
    private Set<NurseEntity> nurses = new HashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "doctorTeam", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private Set<PatientEntity> patients = new HashSet<>();

}
