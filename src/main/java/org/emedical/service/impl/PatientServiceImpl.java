package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Patient;
import org.emedical.models.entities.PatientEntity;
import org.emedical.models.entities.TeamEntity;
import org.emedical.models.requests.PatientRequest;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.repositories.MedicalRecordEntityRepository;
import org.emedical.repositories.PatientEntityRepository;
import org.emedical.service.PatientService;
import org.emedical.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientEntityRepository patientRepository;
    private final AppointmentEntityRepository appointmentRepository;
    private final MedicalRecordEntityRepository medicalRecordRepository;

    private final TeamService teamService;

    private final ModelMapper modelMapper;

    @Override
    public List<Patient> getAllPatients() {
        return patientRepository.findAll()
                .stream()
                .map((p) -> modelMapper.map(p, Patient.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Patient> getAllPatientsByTeamId(Integer teamId) {
        return patientRepository.getPatientEntitiesByTeam_teamId(teamId)
                .stream()
                .map(p -> modelMapper.map(p, Patient.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public Patient getPatientById(Integer id) throws NotFoundException {
//        return modelMapper.map(patientRepository.findPatientEntityById(id)
//                .orElseThrow(() -> new NotFoundException("Patient not found")), Patient.class);
//    }

    @Override
    public Patient createPatient(PatientRequest request) throws NotFoundException {

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setFirstName(request.getFirstName());
        patientEntity.setLastName(request.getLastName());
        patientEntity.setJmb(request.getJmb());
        patientEntity.setPioNumber(request.getPioNumber());
        if (request.getTeamId() != null) {
            patientEntity.setTeam(teamService.findExitingTeam(request.getTeamId()));
        }

        return modelMapper.map(patientRepository.save(patientEntity), Patient.class);
    }

    @Override
    public Patient updatePatient(Integer id, PatientRequest request) throws NotFoundException {
        PatientEntity patientEntity = patientRepository.findPatientEntityById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        TeamEntity teamEntity = null;

        try {
            teamEntity = teamService.findExitingTeam(request.getTeamId());
        } catch (NotFoundException e) {
            teamEntity = null;
        }
        

        patientEntity.setFirstName(request.getFirstName());
        patientEntity.setLastName(request.getLastName());
        patientEntity.setPioNumber(request.getPioNumber());
        patientEntity.setTeam(teamEntity);

        patientRepository.save(patientEntity);
        return modelMapper.map(patientEntity, Patient.class);
    }

    @Override
    public void deletePatient(Integer id) throws NotFoundException, BadRequestException {
        PatientEntity patientEntity = patientRepository.findPatientEntityById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found"));

        if (appointmentRepository.existsAppointmentEntitiesByPatient_Id(id)
                || medicalRecordRepository.existsMedicalRecordEntitiesByPatient_Id(id)) {
            throw new BadRequestException("Patient have appointments and medical records reference to them");
        }

        patientRepository.delete(patientEntity);
    }

}
