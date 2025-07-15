package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<AppointmentEntity> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<MedicalRecordEntity>  medicalRecords;

    //dodati i timove


}
