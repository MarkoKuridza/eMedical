package org.emedical.repositories;

import org.emedical.models.entities.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorEntityRepository extends JpaRepository<DoctorEntity, Integer> {
    Optional<DoctorEntity> getDoctorEntityByTeam_TeamId(Integer id);

    boolean existsDoctorEntityByUsername(String username);

//    boolean existsDoctorEntityByTeam_TeamId(Integer teamId);
//
//    Optional<DoctorEntity> findDoctorEntitiesByIdAndTeam_TeamId(Integer id, Integer teamId);
}
