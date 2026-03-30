package org.emedical.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping("/create-record")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> processPatient(@RequestBody MedicalRecordRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {
        var record = medicalRecordService.createMedicalRecord(request, userDetails.getUsername());
        return ResponseEntity.ok(record);
    }

    //pristup pacijentovom zdravstvenom kartonu
    @PreAuthorize("hasRole('DOCTOR')")
    @GetMapping("/{patientId}")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatientsId(@PathVariable Integer patientId) {
        List<MedicalRecord> patientsMedicalRecord = medicalRecordService.getAllMedicalRecordsByPatientId(patientId);

        return ResponseEntity.ok(patientsMedicalRecord);
    }
}
