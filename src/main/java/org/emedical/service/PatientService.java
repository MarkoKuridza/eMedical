package org.emedical.service;

import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Patient;
import org.emedical.models.requests.PatientRequest;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();

    List<Patient> getAllPatientsByTeamId(Integer teamId);

//    Patient getPatientById(Integer id) throws NotFoundException;

    Patient createPatient(PatientRequest request) throws NotFoundException;

    Patient updatePatient(Integer id, PatientRequest request) throws NotFoundException;

    void deletePatient(Integer id) throws NotFoundException, BadRequestException;
}
