package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.models.entities.PatientEntity;
import org.emedical.repositories.PatientEntityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {
    private final PatientEntityRepository repository;

    @GetMapping
    public List<PatientEntity> findAll(){
        return repository.findAll();
    }
}
