package org.emedical.repositories;

import org.emedical.models.entities.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MedicalRecordEntityRepository extends JpaRepository<MedicalRecordEntity, Integer> {
//    List<MedicalRecordEntity> getMedicalRecordEntitiesByPatient_IdOrderByCreatedAtDesc(Integer id);

    Optional<MedicalRecordEntity> findMedicalRecordEntitiesByAppointment_Id(Integer appointmentId);

    List<MedicalRecordEntity> getMedicalRecordEntitiesByPatient_IdAndPatient_Team_TeamIdOrderByCreatedAtDesc(Integer patientId, Integer teamId);

//    Optional<MedicalRecordEntity> findMedicalRecordEntitiesByAppointment_IdAndAppointment_Team_TeamId(Integer appointmentId, Integer teamId);

    boolean existsMedicalRecordEntitiesByDoctor_Id(Integer doctorId);

    boolean existsMedicalRecordEntitiesByPatient_Id(Integer patientId);
}
