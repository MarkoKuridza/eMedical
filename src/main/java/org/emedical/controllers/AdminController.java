package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.repositories.PatientEntityRepository;
import org.emedical.repositories.TeamEntityRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    //repozitorijumi ne bi trebali ovdje
    private final DoctorEntityRepository doctorRepository;
    private final NurseEntityRepository nurseRepository;
    private final PatientEntityRepository patientRepository;
    private final TeamEntityRepository teamRepository;

    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Long> getSummary() {
        return Map.of(
                "doctors", doctorRepository.count(),
                "nurses", nurseRepository.count(),
                "patients", patientRepository.count(),
                "teams", teamRepository.count()
        );
    }

}
