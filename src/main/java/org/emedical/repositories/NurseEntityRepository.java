package org.emedical.repositories;

import org.emedical.models.entities.NurseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NurseEntityRepository extends JpaRepository<NurseEntity, Integer> {
    Optional<NurseEntity> findNurseEntitiesByIdAndTeam_TeamId(Integer id, Integer teamId);

    Optional<NurseEntity> findNurseEntityById(Integer id);

    boolean existsNurseEntityByUsername(String username);
}
