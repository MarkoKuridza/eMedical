package org.emedical.controllers;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.service.AppointentService;
import org.emedical.service.JwtService;
import org.emedical.service.MedicalRecordService;
import org.emedical.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final AppointentService appointentService;
    private final JwtService jwtService;
    private final MedicalRecordService medicalRecordService;
    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<String> getAllDoctors() {
        return ResponseEntity.ok("Get all doctors");
    }

    @GetMapping("/{id}/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByDoctorId(@PathVariable Integer id, HttpServletRequest request) {
        if (!hasAccess(id, request)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<Appointment> appointments = appointentService.getAllAppointmentsByDoctorId(id);
        return ResponseEntity.ok(appointments);
    }

    //moze se isto uraditi sa @PreAuthorize sa custom provjerom i bolje izgleda, ali mi se to ne da raditi
    private boolean hasAccess(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); //skida "Bearer "
        Claims claim = jwtService.extractAllClaims(token);
        Integer tokenDoctorId = (Integer) claim.get("doctorId");

        return id.equals(tokenDoctorId);
    }

    @PostMapping("/process-patient")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> processPatient(@RequestBody MedicalRecordRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {
        var record = medicalRecordService.createMedicalRecord(request, userDetails.getUsername());
        return ResponseEntity.ok(record);
    }


    //pristup svim pacijentima porodicnog doktora
//    @GetMapping("/{id}/patients")
//    public ResponseEntity<List<Patient>> getAllPatientsByDoctorId(@PathVariable Integer doctorId, HttpServletRequest request) {
//    }

    //pristup pacijentu od strane porodicnog doktora
    //@GetMapping("/{id}/patients/{patient-id}")
//    public ResponseEntity<List<Patient>> getPatientByDoctorId(@PathVariable Integer doctorId, @PathVariable Integer patientId, HttpServletRequest request){
//    }

    //pristup pacijentovom zdravstvenom kartonu
    @GetMapping("/{doctorId}/patients/{patientId}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatientsId(@PathVariable Integer doctorId, @PathVariable Integer patientId, HttpServletRequest request) {
        List<MedicalRecord> patientsMedicalRecord = medicalRecordService.getAllMedicalRecordsByPatientId(patientId);

        return ResponseEntity.ok(patientsMedicalRecord);
    }


}
