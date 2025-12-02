package org.emedical.controllers;

import java.util.List;

import org.emedical.models.dto.DoctorTeam;
import org.emedical.models.requests.DoctorRequest;
import org.emedical.models.requests.DoctorTeamRequest;
import org.emedical.models.requests.NurseRequest;
import org.emedical.models.requests.PatientRequest;
import org.emedical.service.JwtService;
import org.emedical.service.impl.DoctorTeamServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/api/admin/doctor-teams")
public class AdminDoctorTeamController {
    
    private final JwtService jwtService;
    private final DoctorTeamServiceImpl teamService;

    @GetMapping
    public List<DoctorTeam> getAllAdmins() {
        return teamService.findAll();
    }

    @PostMapping
    public ResponseEntity<DoctorTeam> createTema(@RequestBody DoctorTeamRequest team) {
        DoctorTeam created = teamService.saveTeam(team);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorTeam> editTeam(@PathVariable Integer id, @RequestBody DoctorTeam team) {
        team.setId(id);
        DoctorTeam updated = teamService.editTeam(team);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{team_id}/doctor")
    public ResponseEntity<Void> addDoctor(@PathVariable Integer team_id, @RequestBody DoctorRequest doctor) {
        teamService.addDoctor(team_id, doctor.getDoctorId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{team_id}/doctor/{doctor_id}")
    public ResponseEntity<Void> removeDoctor(@PathVariable Integer team_id, @PathVariable Integer doctor_id) {
        teamService.removeDoctor(team_id, doctor_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{team_id}/nurses")
    public ResponseEntity<Void> addNurse(@PathVariable Integer team_id, @RequestBody NurseRequest nurse) {
        teamService.addNurse(team_id, nurse.getNurseId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{team_id}/nurses/{nurse_id}")
    public ResponseEntity<Void> removeNurse(@PathVariable Integer team_id, @PathVariable Integer nurse_id) {
        teamService.removeNurse(team_id, nurse_id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{team_id}/patients")
    public ResponseEntity<Void> addPatient(@PathVariable Integer team_id, @RequestBody PatientRequest patient) {
        teamService.addPatient(team_id, patient.getPatientId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{team_id}/patients/{patient_id}")
    public ResponseEntity<Void> removePatient(@PathVariable Integer team_id, @PathVariable Integer patient_id) {
        teamService.removePatient(team_id, patient_id);
        return ResponseEntity.ok().build();
    }
}
