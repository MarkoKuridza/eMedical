package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "medical_record")
public class MedicalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "diagnosis", nullable = false)
    private String diagnosis;

    @Basic
    @Column(name = "prescription", nullable = false)
    private String prescription;

    @Basic
    @Column(name = "created_At", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private AppointmentEntity appointment;
}
