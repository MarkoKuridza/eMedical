package org.emedical.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.Doctor;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.TeamEntity;
import org.emedical.models.enums.Role;
import org.emedical.models.requests.DoctorRequest;
import org.emedical.models.responses.DoctorResponse;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.repositories.MedicalRecordEntityRepository;
import org.emedical.repositories.UserEntityRepository;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AppointmentService;
import org.emedical.service.DoctorService;
import org.emedical.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorEntityRepository doctorRepository;
    private final UserEntityRepository userRepository;
    private final AppointmentEntityRepository appointmentRepository;
    private final MedicalRecordEntityRepository medicalRecordRepository;

    private final AppointmentService appointmentService;
    private final TeamService teamService;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DoctorResponse getDoctorByTeamId(Integer teamId) {
        DoctorEntity doctor = doctorRepository.getDoctorEntityByTeam_TeamId(teamId)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        DoctorResponse doctorResponse = new DoctorResponse(doctor.getFirstName(), doctor.getLastName());
        return doctorResponse;
    }

    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(d -> modelMapper.map(d, Doctor.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public List<WaitingRoom> getDoctorQueue(Integer doctorId) {
//        return waitingRoomService.getDoctorQueue(doctorId);
//    }

    @Override
    public List<Appointment> getDoctorAppointments(Integer doctorId) {
        return appointmentService.getDoctorAppointments(doctorId);
    }

    @Override
    public Doctor registerDoctor(DoctorRequest request) {
        if (doctorRepository.existsDoctorEntityByUsername(request.getUsername())) {
            throw new BadRequestException("Username taken!");
        }

        DoctorEntity doctorEntity = new DoctorEntity();

        doctorEntity.setUsername(request.getUsername());
        doctorEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        doctorEntity.setFirstName(request.getFirstName());
        doctorEntity.setLastName(request.getLastName());
        doctorEntity.setJmb(request.getJmb());
        doctorEntity.setSpecialization(request.getSpecialization());
        doctorEntity.setRole(Role.DOCTOR);
        doctorEntity.setTeam(resolveDoctorTeam(request.getTeamId(), null));

        doctorRepository.save(doctorEntity);

        return modelMapper.map(doctorEntity, Doctor.class);
    }

    //isto kao i kod update za Nurse, PATCH!!
    //ali mi se ne da raditi
    @Override
    public Doctor updateDoctor(Integer id, DoctorRequest request) {
        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));

        if (request.getUsername() != null && !request.getUsername().isBlank()
                && !request.getUsername().equals(doctorEntity.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username taken!");
            }
            doctorEntity.setUsername(request.getUsername());
        }

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            doctorEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        doctorEntity.setFirstName(request.getFirstName());
        doctorEntity.setLastName(request.getLastName());
        doctorEntity.setSpecialization(request.getSpecialization());

        if (request.getTeamId() != null) {
            doctorEntity.setTeam(resolveDoctorTeam(request.getTeamId(), id));
        }

        doctorRepository.save(doctorEntity);
        return modelMapper.map(doctorEntity, Doctor.class);
    }

    @Override
    public void deleteDoctor(Integer id) {
        DoctorEntity doctorEntity = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor not found"));
        if (appointmentRepository.existsAppointmentEntitiesByDoctor_Id(id)
                || medicalRecordRepository.existsMedicalRecordEntitiesByDoctor_Id(id)) {
            throw new BadRequestException("Doctor cannot be deleted while appointments or medical records reference them.");
        }
        doctorRepository.delete(doctorEntity);
    }

    @Override
    @Transactional
    public Appointment startAppointment(Integer appointmentId, CustomUserDetails doctor) throws NotFoundException {
        return appointmentService.startAppointment(appointmentId, doctor);
    }

    private TeamEntity resolveDoctorTeam(Integer teamId, Integer doctorId) throws NotFoundException {
        if (teamId == null) {
            return null;
        }

        TeamEntity team = teamService.findExitingTeam(teamId);
        doctorRepository.getDoctorEntityByTeam_TeamId(teamId)
                .filter(existingDoctor -> !existingDoctor.getId().equals(doctorId))
                .ifPresent(existingDoctor -> {
                    throw new BadRequestException("Team already has a doctor.");
                });

        return team;
    }
}
