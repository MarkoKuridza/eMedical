package org.emedical.repositories;

import org.emedical.models.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Integer> {
    List<AppointmentEntity> getAllByDoctor_Id(Integer id);
}
