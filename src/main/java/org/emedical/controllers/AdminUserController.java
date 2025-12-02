package org.emedical.controllers;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.Nurse;
import org.emedical.models.dto.Patient;
import org.emedical.service.JwtService;
import org.emedical.service.impl.DoctorServiceImpl;
import org.emedical.service.impl.NurseServiceImpl;
import org.emedical.service.impl.PatientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/user-controll")
public class AdminUserController {
    private final JwtService jwtService;
    private final DoctorServiceImpl doctorService;
    private final NurseServiceImpl nurseService;
    private final PatientServiceImpl patientService;

    //Doktor

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable int id) {
        try{
            Doctor doctor = doctorService.findById(id);
            return ResponseEntity.ok(doctor);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/doctors")
    public Doctor createDoctor(@RequestBody Doctor doctor) {
        return doctorService.createDoctor(doctor);
    }

    @PutMapping("/doctors/edit/{id}")
    public Doctor editDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        return doctorService.editDoctor(doctor);
    }

    @PutMapping("/doctors/{id}/activate")
    public ResponseEntity<String> activateDoctor(@PathVariable Integer id) {
        doctorService.setActive(id);
        return ResponseEntity.ok("Doctor with ID " + id + " activated successfully");
    }
    
    @PutMapping("/doctors/{id}/deactivate")
    public ResponseEntity<String> deactivateDoctor(@PathVariable Integer id) {
        doctorService.setInactive(id);
        return ResponseEntity.ok("Doctor with ID " + id + " deactivated successfully");
    }
    
    //Sestra

    @GetMapping("/nurses")
    public List<Nurse> getAllNurses() {
        return nurseService.getAllNurses();
    }

    @GetMapping("/nurses/{id}")
    public ResponseEntity<Nurse> getNurseById(@PathVariable int id) {
        try{
            Nurse nurse = nurseService.findById(id);
            return ResponseEntity.ok(nurse);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/nurses")
    public Nurse createNurse(@RequestBody Nurse nurse) {
        return nurseService.createNurse(nurse);
    }

    @PutMapping("/nurses/edit/{id}")
    public Nurse editNurse(@PathVariable Integer id, @RequestBody Nurse nurse) {
        return nurseService.editNurse(nurse);
    }

    @PutMapping("/nurses/{id}/activate")
    public ResponseEntity<String> activateNurse(@PathVariable Integer id) {
        nurseService.setActive(id);
        return ResponseEntity.ok("Nurse with ID " + id + " activated successfully");
    }
    
    @PutMapping("/nurses/{id}/deactivate")
    public ResponseEntity<String> deactivateNurse(@PathVariable Integer id) {
        nurseService.setInactive(id);
        return ResponseEntity.ok("Nurse with ID " + id + " deactivated successfully");
    }

    //Pacijent

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAllPatients();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable int id) {
        try{
            Patient patient = patientService.findById(id);
            return ResponseEntity.ok(patient);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.savePatient(patient);
    }

    @PutMapping("/patients/edit/{id}")
    public Patient editPatient(@PathVariable Integer id, @RequestBody Patient patient) {
        return patientService.editPatient(patient);
    }
}
