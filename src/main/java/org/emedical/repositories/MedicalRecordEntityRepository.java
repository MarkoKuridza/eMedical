package org.emedical.repositories;

import org.emedical.models.entities.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalRecordEntityRepository extends JpaRepository<MedicalRecordEntity, Integer> {
    List<MedicalRecordEntity> getAllByPatient_Id(Integer id);
}
