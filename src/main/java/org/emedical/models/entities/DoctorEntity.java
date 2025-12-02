package org.emedical.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.emedical.models.dto.Patient;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "doctor")
public class DoctorEntity extends UserEntity {

    @Basic
    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Basic
    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Basic
    @Column(name = "specialization", nullable = false)
    private String specialization;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id", referencedColumnName = "id")
    private TeamEntity team;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<PatientEntity> patients;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<MedicalRecordEntity>  medicalRecords;
}
