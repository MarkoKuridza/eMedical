package org.emedical.repositories;

import org.emedical.models.entities.WaitingRoomEntity;
import org.emedical.models.enums.WaitingRoomStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface WaitingRoomEntityRepository extends JpaRepository<WaitingRoomEntity, Integer> {
    Optional<WaitingRoomEntity> findWaitingRoomEntitiesByAppointment_Id(Integer appointmentId);

    Optional<WaitingRoomEntity> findTopByTeam_TeamIdOrderByQueueNumberDesc(Integer teamId);

    List<WaitingRoomEntity> findWaitingRoomEntitiesByTeam_TeamIdAndStatusInOrderByQueueNumberAsc(Integer teamId, Collection<WaitingRoomStatus> statuses);

    List<WaitingRoomEntity> findWaitingRoomEntitiesByDoctor_IdAndStatusInOrderByQueueNumberAsc(Integer doctorId, Collection<WaitingRoomStatus> statuses);
}
