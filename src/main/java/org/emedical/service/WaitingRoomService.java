package org.emedical.service;

import org.emedical.models.dto.WaitingRoom;

import java.util.List;

public interface WaitingRoomService {
    List<WaitingRoom> getWaitingRoomByTeamId(Integer teamId);

    List<WaitingRoom> getDoctorQueue(Integer doctorId);

    List<WaitingRoom> getReadyForNurse(Integer teamId);
}
