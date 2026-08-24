package org.emedical.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Nurse;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.enums.Role;
import org.emedical.models.requests.NurseRequest;
import org.emedical.repositories.AppointmentEntityRepository;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.repositories.UserEntityRepository;
import org.emedical.service.NurseService;
import org.emedical.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class NurseServiceImpl implements NurseService {
    private final NurseEntityRepository nurseRepository;
    private final UserEntityRepository userRepository;
    private final AppointmentEntityRepository appointmentRepository;

    private final TeamService teamService;

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    //crud operacije
    @Override
    public List<Nurse> findAll() {
        return nurseRepository.findAll()
                .stream()
                .map((n) -> modelMapper.map(n, Nurse.class))
                .collect(Collectors.toList());
    }

//    @Override
//    public Nurse findById(Integer id) throws NotFoundException {
//        return modelMapper.map(nurseRepository.findNurseEntityById(id)
//                .orElseThrow(() -> new NotFoundException("Nurse not found")), Nurse.class);
//    }

    @Override
    public Nurse create(NurseRequest request) {
        if (nurseRepository.existsNurseEntityByUsername(request.getUsername())) {
            throw new BadRequestException("Username taken!");
        }

        NurseEntity nurseEntity = new NurseEntity();

        nurseEntity.setUsername(request.getUsername());
        nurseEntity.setFirstName(request.getFirstName());
        nurseEntity.setLastName(request.getLastName());
        nurseEntity.setJmb(request.getJmb());
        nurseEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        nurseEntity.setRole(Role.NURSE);
        if (request.getTeamId() != null) {
            nurseEntity.setTeam(teamService.findExitingTeam(request.getTeamId()));
        }

        nurseRepository.save(nurseEntity);
        return modelMapper.map(nurseEntity, Nurse.class);
    }

    //ova metoda je za @PutMapping jer se cijeli entitet proslijedjuje i mijenja sa postojecim
    //za parcijalni update koristi @PatchMapping jer to mijenja samo polja koja mu proslijedim (username, password i sl.)
    //generalno bi se cesce radio parcijalni update
    //al mi se ne da raditi drugacije
    //Bane ako ti se da mozes i to implementirati
    //ne moras pisati metodu za svako polje, nego mozes jednu metodu
    //pa uz pomoc ModelMappera da prepozna koja polja mijenajti(ako je proslijedjeno neko polje null, to polje ne dira)
    @Override
    public Nurse update(Integer id, NurseRequest request) throws NotFoundException {
        NurseEntity nurseEntity = nurseRepository.findNurseEntityById(id)
                .orElseThrow(() -> new NotFoundException("Nurse not found"));

        if (request.getUsername() != null && !request.getUsername().isBlank()
                && !request.getUsername().equals(nurseEntity.getUsername())) {
            if (userRepository.existsByUsername(request.getUsername())) {
                throw new BadRequestException("Username taken!");
            }
            nurseEntity.setUsername(request.getUsername());
        }
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            nurseEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        nurseEntity.setFirstName(request.getFirstName());
        nurseEntity.setLastName(request.getLastName());

        if (request.getTeamId() != null) {
            nurseEntity.setTeam(teamService.findExitingTeam(request.getTeamId()));
        }
        nurseRepository.save(nurseEntity);
        return modelMapper.map(nurseEntity, Nurse.class);
    }

    @Override
    public void delete(Integer id) throws NotFoundException {
        NurseEntity nurseEntity = nurseRepository.findNurseEntityById(id)
                .orElseThrow(() -> new NotFoundException("Nurse not found"));
        if (appointmentRepository.existsAppointmentEntitiesByNurse_Id(id)) {
            throw new BadRequestException("Nurse cannot be deleted while appointments reference them.");
        }

        nurseRepository.delete(nurseEntity);
    }
}
