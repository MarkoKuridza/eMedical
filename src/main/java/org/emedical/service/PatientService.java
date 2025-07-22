package org.emedical.service;

import org.emedical.models.dto.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAllPatients();
}
