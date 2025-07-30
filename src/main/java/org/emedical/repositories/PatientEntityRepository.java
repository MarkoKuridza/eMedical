package org.emedical.repositories;


import org.emedical.models.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientEntityRepository extends JpaRepository<PatientEntity, Integer> {
}
