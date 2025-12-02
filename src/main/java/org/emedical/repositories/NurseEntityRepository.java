package org.emedical.repositories;

import org.emedical.models.entities.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseEntityRepository extends JpaRepository<NurseEntity, Integer> {
}
