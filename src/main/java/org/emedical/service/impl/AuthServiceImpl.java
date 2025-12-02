package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;
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
        UserDetails user = (UserDetails) authentication.getPrincipal();

        Doctor doctor = modelMapper.map(repository.findByUsername(user.getUsername()).orElseThrow(NotFoundException::new), Doctor.class);
        Map<String, Object> claims = new HashMap<>();
        claims.put("doctorId", doctor.getId());

        String token = jwtService.generateToken(claims, user);

        return new LoginResponse(token);
    }
}
