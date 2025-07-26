package org.emedical.repositories;

import java.util.Optional;

import org.emedical.models.entities.AdminEntity;
import org.emedical.models.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientEntityRepository extends JpaRepository<PatientEntity, Integer> {
    //Optional<PatientEntity> findByUsername(String username);
}
