package org.emedical.repositories;

import org.emedical.models.entities.MedicalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalRecordEntityRepository extends JpaRepository<MedicalRecordEntity, Integer> {
}
