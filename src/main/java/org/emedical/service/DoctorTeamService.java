package org.emedical.service;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.DoctorTeam;
import org.emedical.models.requests.DoctorTeamRequest;

public interface DoctorTeamService {
    
    List<DoctorTeam> findAll();
    DoctorTeam findById(Integer id) throws NotFoundException;
    DoctorTeam saveTeam(DoctorTeamRequest team);
    DoctorTeam editTeam(DoctorTeam team);

    void addDoctor(Integer team_id, Integer doctor_id);
    void removeDoctor(Integer team_id, Integer doctor_id);

    void addNurse(Integer team_id, Integer nurse_id);
    void removeNurse(Integer team_id, Integer nurse_id);

    void addPatient(Integer team_id, Integer patient_id);
    void removePatient(Integer team_id, Integer patient_id);
}
