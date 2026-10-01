package org.emedical.controllers;

import java.util.List;

import org.emedical.models.dto.Diagnosis;
import org.emedical.service.DiagnosisService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController 
@RequestMapping("/api/diagnoses")
@RequiredArgsConstructor 
public class DiagnosisController {
    
    private final DiagnosisService diagnosisService;

    @GetMapping("/all")
    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisService.getAllDiagnosis();
    }
    
}
