package org.emedical.service;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;

public interface DoctorService {
    List<Doctor> getAllDoctors();
    Doctor findById(Integer id) throws NotFoundException;
    Doctor createDoctor(Doctor doctor);
    Doctor editDoctor(Doctor doctor);
    void setActive(Integer id);
    void setInactive(Integer id);
}
