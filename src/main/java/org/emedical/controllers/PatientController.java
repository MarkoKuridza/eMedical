package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.Patient;
import org.emedical.service.MedicalRecordService;
import org.emedical.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;
    private final MedicalRecordService medicalRecordService;

    //pristup generalno svim pacijentima
    @GetMapping()
    public ResponseEntity<List<Patient>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }


//    //pristup pacijentu i njegovom zdravstvenom kartonu
//    @GetMapping("/patients/{patientId}")
//    public ResponseEntity<List<Patient>> getPatient(@PathVariable Integer patientId){
//
//    }
}
