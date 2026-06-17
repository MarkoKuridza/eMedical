package org.emedical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Patient;
import org.emedical.models.requests.PatientRequest;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> createPatient(@RequestBody PatientRequest request) throws NotFoundException {
        return ResponseEntity.ok(patientService.createPatient(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Patient> updatePatient(@PathVariable Integer id,
                                                 @Valid @RequestBody PatientRequest request) throws NotFoundException {
        return ResponseEntity.ok(patientService.updatePatient(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id) throws NotFoundException {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/team-patients")
    public ResponseEntity<List<Patient>> getAllPatientsByTeamId(@AuthenticationPrincipal CustomUserDetails user) {
        List<Patient> patients = patientService.getAllPatientsByTeamId(user.getTeamId());
        return ResponseEntity.ok(patients);
    }
}
