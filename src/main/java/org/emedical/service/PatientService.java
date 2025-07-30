package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
    Patient findById(Integer id) throws NotFoundException;
    Patient savePatient(Patient patient);
    Patient editPatient(Patient patient);
}
