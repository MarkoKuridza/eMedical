package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.Patient;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.DoctorService;
import org.emedical.service.MedicalRecordService;
import org.emedical.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient")
public class PatientController {

    private final PatientService patientService;
    private final DoctorService doctorService;

    //pristup svim pacijentima doktora/tima
    @GetMapping("/patients")
    public ResponseEntity<List<Patient>> getAllPatientsByTeamId(@AuthenticationPrincipal CustomUserDetails user) {
        Doctor doctor = doctorService.getDoctorByTeamId(user.getTeamId());

        List<Patient> patients = patientService.getAllPatientsByDoctorId(doctor.getId());
        return ResponseEntity.ok(patients);
    }


//    //pristup pacijentu i njegovom zdravstvenom kartonu
//    @GetMapping("/patients/{patientId}")
//    public ResponseEntity<List<Patient>> getPatient(@PathVariable Integer patientId){
//
//    }
}
