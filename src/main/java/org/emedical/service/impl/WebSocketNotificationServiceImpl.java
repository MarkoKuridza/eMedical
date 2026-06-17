package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.dto.WaitingRoomMessage;
import org.emedical.service.WebSocketNotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WebSocketNotificationServiceImpl implements WebSocketNotificationService {
    private final SimpMessagingTemplate template;

    @Override
    public void notifyDoctorPatientArrived(Integer doctorId, WaitingRoom waitingRoom) {
        WaitingRoomMessage message = new WaitingRoomMessage(
                WaitingRoomMessage.Type.PATIENT_ARRIVED,
                waitingRoom,
                "Pacijent " + waitingRoom.getPatientFirstName() + " " + waitingRoom.getPatientLastName()
                        + " je stigao. Redni broj: " + waitingRoom.getQueueNumber(),
                LocalDateTime.now()
        );
        template.convertAndSend("/topic/doctor/" + doctorId, message);
        template.convertAndSend("/topic/waiting-room/" + waitingRoom.getTeamId(), message);
    }

    @Override
    public void notifyNursePatientReady(Integer teamId, WaitingRoom waitingRoom) {
        WaitingRoomMessage message = new WaitingRoomMessage(
                WaitingRoomMessage.Type.DOCTOR_FINISHED,
                waitingRoom,
                "Doktor je završio pregled pacijenta " + waitingRoom.getPatientFirstName()
                        + " " + waitingRoom.getPatientLastName() + ". Spreman za završetak od strane sestre.",
                LocalDateTime.now()
        );

        template.convertAndSend("/topic/waiting-room/" + teamId, message);
    }

    @Override
    public void notifyTeamAppointmentCompleted(Integer teamId, WaitingRoom waitingRoom) {
        WaitingRoomMessage message = new WaitingRoomMessage(
                WaitingRoomMessage.Type.APPOINTMENT_COMPLETED,
                waitingRoom,
                "Pregled pacijenta " + waitingRoom.getPatientFirstName()
                        + " " + waitingRoom.getPatientLastName() + " je u potpunosti završen.",
                LocalDateTime.now()
        );

        template.convertAndSend("/topic/waiting-room/" + teamId, message);
    }
}
