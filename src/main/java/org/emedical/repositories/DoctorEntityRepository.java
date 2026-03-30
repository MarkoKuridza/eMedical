package org.emedical.repositories;

import org.emedical.models.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, Integer> {
    Optional<DoctorEntity> findById(Integer id);
    DoctorEntity getDoctorEntityByTeam_TeamId(Integer id);

    Optional<DoctorEntity> findByUsername(String username);
}
