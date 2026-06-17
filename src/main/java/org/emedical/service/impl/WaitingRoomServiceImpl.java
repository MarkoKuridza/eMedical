package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.enums.WaitingRoomStatus;
import org.emedical.repositories.WaitingRoomEntityRepository;
import org.emedical.service.WaitingRoomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WaitingRoomServiceImpl implements WaitingRoomService {

    private final WaitingRoomEntityRepository waitingRoomRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<WaitingRoom> getWaitingRoomByTeamId(Integer teamId) {
        return waitingRoomRepository.findWaitingRoomEntitiesByTeam_TeamIdAndStatusInOrderByQueueNumberAsc(
                        teamId,
                        List.of(WaitingRoomStatus.WAITING_FOR_DOCTOR, WaitingRoomStatus.READY_FOR_NURSE)
                ).stream()
                .map(w -> modelMapper.map(w, WaitingRoom.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WaitingRoom> getDoctorQueue(Integer doctorId) {
        return waitingRoomRepository.findWaitingRoomEntitiesByDoctor_IdAndStatusInOrderByQueueNumberAsc(
                        doctorId,
                        List.of(WaitingRoomStatus.WAITING_FOR_DOCTOR)
                ).stream()
                .map(w -> modelMapper.map(w, WaitingRoom.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<WaitingRoom> getReadyForNurse(Integer teamId) {
        return waitingRoomRepository.findWaitingRoomEntitiesByTeam_TeamIdAndStatusInOrderByQueueNumberAsc(
                        teamId,
                        List.of(WaitingRoomStatus.READY_FOR_NURSE)
                ).stream()
                .map(w -> modelMapper.map(w, WaitingRoom.class))
                .collect(Collectors.toList());
    }
}
