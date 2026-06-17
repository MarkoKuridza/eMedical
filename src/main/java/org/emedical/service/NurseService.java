package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Nurse;
import org.emedical.models.requests.NurseRequest;

import java.util.List;

public interface NurseService {

    //crud
    List<Nurse> findAll();

//    Nurse findById(Integer id) throws NotFoundException;

    Nurse create(NurseRequest request);

    Nurse update(Integer id, NurseRequest request) throws NotFoundException;

    void delete(Integer id) throws NotFoundException;

    //druge operacije


}
