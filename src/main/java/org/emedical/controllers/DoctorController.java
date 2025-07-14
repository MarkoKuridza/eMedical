package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.DoctorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorEntityRepository repository;
    private final DoctorService doctorService;

    @GetMapping
    List<DoctorEntity> findAll(){
        return repository.findAll();
    }

}
