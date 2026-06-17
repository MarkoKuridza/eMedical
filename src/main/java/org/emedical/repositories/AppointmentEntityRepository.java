package org.emedical.repositories;

import org.emedical.models.entities.AppointmentEntity;
import org.emedical.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentEntityRepository extends JpaRepository<AppointmentEntity, Integer> {
    List<AppointmentEntity> findAppointmentEntitiesByTeam_TeamIdOrderByAppointmentDateAsc(Integer id);

    List<AppointmentEntity> findAppointmentEntitiesByTeam_TeamIdAndAppointmentStatusOrderByAppointmentDateAsc(Integer teamId, Status status);

    Optional<AppointmentEntity> findAppointmentEntitiesByIdAndTeam_TeamId(Integer id, Integer teamId);

    List<AppointmentEntity> findAppointmentEntitiesByDoctor_IdOrderByAppointmentDateAsc(Integer doctorId);

    List<AppointmentEntity> findAppointmentEntitiesByDoctor_IdAndAppointmentDateAndAppointmentStatusNot(Integer doctorId, LocalDateTime appointmentDate, Status status);

    boolean existsAppointmentEntitiesByDoctor_Id(Integer doctorId);

    boolean existsAppointmentEntitiesByNurse_Id(Integer nurseId);

    boolean existsAppointmentEntitiesByPatient_Id(Integer patientId);
}
