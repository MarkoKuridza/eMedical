package org.emedical.repositories;

import java.util.Optional;

import org.emedical.models.entities.DoctorTeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorTeamRepository extends JpaRepository<DoctorTeamEntity, Integer> {
    Optional<DoctorTeamEntity> findByDoctor_id(Integer id);
}
