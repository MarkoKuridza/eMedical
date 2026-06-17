package org.emedical.repositories;

import org.emedical.models.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PatientEntityRepository extends JpaRepository<PatientEntity, Integer> {
    List<PatientEntity> getPatientEntitiesByTeam_teamId(Integer teamId);

    Optional<PatientEntity> findPatientEntityById(Integer id);

//    Optional<PatientEntity> findPatientEntityByIdAndTeam_TeamId(Integer id, Integer teamId);

}
