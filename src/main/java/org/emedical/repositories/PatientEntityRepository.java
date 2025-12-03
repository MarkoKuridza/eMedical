package org.emedical.repositories;

import org.emedical.models.entities.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientEntityRepository extends JpaRepository<PatientEntity, Integer> {
    List<PatientEntity> getAllByDoctor_Id(Integer doctorId);

}
