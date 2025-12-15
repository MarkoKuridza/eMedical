package org.emedical.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.MedicalRecord;
import org.emedical.models.dto.Patient;
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

    private final DoctorEntityRepository repository;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors;
        doctors = repository.findAll().stream().map(d -> modelMapper.map(d, Doctor.class)).collect(Collectors.toList());
        return ResponseEntity.ok(doctors);
    }
}
