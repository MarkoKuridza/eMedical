package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.models.entities.AppointmentEntity;
import org.emedical.repositories.AppointmentEntityRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentEntityRepository repository;

    @GetMapping
    public List<AppointmentEntity> findAll(){
        return repository.findAll();
    }
}
