package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;
import org.emedical.models.entities.UserEntity;
import org.emedical.models.enums.Role;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.service.AuthService;
import org.emedical.service.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final DoctorEntityRepository repository;
    private final ModelMapper modelMapper;

    public LoginResponse login(LoginRequest request) throws NotFoundException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //Auth za doktora i admina istovremeno, treba i za sestru uraditi!!
        UserEntity userEntity = repository.findByUsername(userDetails.getUsername())
        .orElseThrow(NotFoundException::new);

        Map<String, Object> claims = new HashMap<>();
        
        if (userEntity.getRole() == Role.DOCTOR) {
            Doctor doctor = modelMapper.map(userEntity, Doctor.class);
            claims.put("doctorId", doctor.getId());
        } else if (userEntity.getRole() == Role.ADMIN) {
            Admin admin = modelMapper.map(userEntity, Admin.class);
            claims.put("adminId", admin.getId());
        }

        String token = jwtService.generateToken(claims, userDetails);

        return new LoginResponse(token);
    }
}
