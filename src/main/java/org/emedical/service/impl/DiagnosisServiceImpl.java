package org.emedical.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.emedical.models.dto.Diagnosis;
import org.emedical.service.DiagnosisService;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service 
public class DiagnosisServiceImpl implements DiagnosisService {
    
    private final List<Diagnosis> diagnoses;

    public DiagnosisServiceImpl(ObjectMapper objectMapper) throws IOException {

        try (InputStream input = getClass()
                .getResourceAsStream("/diagnoses.json")) {

            this.diagnoses = objectMapper.readValue(input, new TypeReference<List<Diagnosis>>() {});
        }
    }

    @Override
    public List<Diagnosis> getAllDiagnosis() {
        return diagnoses;
    }
}
