package org.emedical.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.emedical.models.enums.WaitingRoomStatus;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "waiting_room")
public class WaitingRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_id", nullable = false, unique = true)
    private AppointmentEntity appointment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private TeamEntity team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private DoctorEntity doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id", nullable = false)
    private NurseEntity nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private PatientEntity patient;

    @Basic
    @Column(name = "queue_number", nullable = false)
    private Integer queueNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private WaitingRoomStatus status;

    @Column(name = "arrived_at", nullable = false)
    private LocalDateTime arrivedAt;

    @Column(name = "doctor_notified_at", nullable = false)
    private LocalDateTime doctorNotifiedAt;

    @Column(name = "doctor_finished_at")
    private LocalDateTime doctorFinishedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
