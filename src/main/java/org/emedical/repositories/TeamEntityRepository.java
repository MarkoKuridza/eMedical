package org.emedical.repositories;

import org.emedical.models.entities.TeamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeamEntityRepository extends JpaRepository<TeamEntity, Integer> {
    Optional<TeamEntity> findTeamEntityByTeamId(Integer teamId);

    boolean existsTeamEntityByTeamName(String teamName);
}
