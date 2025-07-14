package org.emedical.repositories;

import org.emedical.models.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, Integer> {
}
