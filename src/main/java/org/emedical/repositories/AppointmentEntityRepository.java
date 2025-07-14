package org.emedical.repositories;

import org.emedical.models.entities.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Integer> {
}
