package org.emedical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.MedicalRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/create-record")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> processPatient(@RequestParam Integer appointmentId,
                                            @Valid @RequestBody MedicalRecordRequest request,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        var medicalRecord = medicalRecordService.finishAppointment(appointmentId, request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @PostMapping("/{appointmentId}/finish")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> finishAppointment(@PathVariable Integer appointmentId,
                                               @Valid @RequestBody MedicalRecordRequest request,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) throws NotFoundException {
        var medicalRecord = medicalRecordService.finishAppointment(appointmentId, request, userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).body(medicalRecord);
    }

    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatientsId(@PathVariable Integer patientId,
                                                                             @AuthenticationPrincipal CustomUserDetails user) {
        List<MedicalRecord> patientsMedicalRecord = medicalRecordService.getAllMedicalRecordsByPatientId(patientId, user);

        return ResponseEntity.ok(patientsMedicalRecord);
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<MedicalRecord> getMedicalRecordByAppointmentId(@PathVariable Integer appointmentId) throws NotFoundException {
        return ResponseEntity.ok(medicalRecordService.getMedicalRecordByAppointmentId(appointmentId));
    }
}
