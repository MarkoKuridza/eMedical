package org.emedical.service;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Nurse;

public interface NurseService {
    List<Nurse> getAllNurses();
    Nurse findById(Integer id) throws NotFoundException;
    Nurse createNurse(Nurse nurse);
    Nurse editNurse(Nurse nurse);
    void setActive(Integer id);
    void setInactive(Integer id);
}
