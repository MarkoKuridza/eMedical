package org.emedical.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.requests.MedicalRecordRequest;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final MedicalRecordService medicalRecordService;
    private final PatientService patientService;

    private final DoctorEntityRepository repository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors;
        doctors = repository.findAll().stream().map(d -> modelMapper.map(d, Doctor.class)).collect(Collectors.toList());
        return ResponseEntity.ok(doctors);
    }

    //prebaci u AppointmentController i izmjeniti api pozive na frontu
//    @GetMapping("/{id}/appointments")
//    public ResponseEntity<List<Appointment>> getAllAppointmentsByDoctorId(@PathVariable Integer id, HttpServletRequest request) {
//        if (!hasAccess(id, request)) {
//            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
//        }
//
//        List<Appointment> appointments = appointmentService.getAllAppointmentsByDoctorId(id);
//        return ResponseEntity.ok(appointments);
//    }

    //moze se isto uraditi sa @PreAuthorize sa custom provjerom i bolje izgleda, ali mi se to ne da raditi
//    private boolean hasAccess(Integer id, HttpServletRequest request) {
//        String token = request.getHeader("Authorization").substring(7); //skida "Bearer "
//        Claims claim = jwtService.extractAllClaims(token);
//        Integer tokenDoctorId = (Integer) claim.get("doctorId");
//
//        return id.equals(tokenDoctorId);
//    }

    //prebaciti u MedicalRecordController i izmjeniti api pozive na frontu
    @PostMapping("/process-patient")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> processPatient(@RequestBody MedicalRecordRequest request,
                                            @AuthenticationPrincipal UserDetails userDetails) throws NotFoundException {
        var record = medicalRecordService.createMedicalRecord(request, userDetails.getUsername());
        return ResponseEntity.ok(record);
    }


    //pristup svim pacijentima porodicnog doktora
    @GetMapping("/{doctorId}/patients")
    public ResponseEntity<List<Patient>> getAllPatientsByDoctorId(@PathVariable Integer doctorId, HttpServletRequest request) {
        List<Patient> doctorsPatients = patientService.getAllPatientsByDoctorId(doctorId);

        return ResponseEntity.ok(doctorsPatients);
    }


    //pristup pacijentovom zdravstvenom kartonu
    @GetMapping("/patients/{patientId}/medical-records")
    public ResponseEntity<List<MedicalRecord>> getMedicalRecordsByPatientsId( @PathVariable Integer patientId, HttpServletRequest request) {
        List<MedicalRecord> patientsMedicalRecord = medicalRecordService.getAllMedicalRecordsByPatientId(patientId);

        return ResponseEntity.ok(patientsMedicalRecord);
    }


}
