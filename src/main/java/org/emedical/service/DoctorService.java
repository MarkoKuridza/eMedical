package org.emedical.service;

import org.emedical.models.dto.Doctor;

public interface DoctorService {

    Doctor getDoctorByTeamId(Integer teamId);
}
