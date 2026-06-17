package org.emedical.service;

import org.emedical.models.dto.WaitingRoom;

public interface WebSocketNotificationService {
    void notifyDoctorPatientArrived(Integer doctorId, WaitingRoom waitingRoom);

    void notifyNursePatientReady(Integer teamId, WaitingRoom waitingRoom);

    void notifyTeamAppointmentCompleted(Integer teamId, WaitingRoom waitingRoom);
}
