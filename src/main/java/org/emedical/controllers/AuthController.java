package org.emedical.controllers;

import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;
import org.emedical.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws NotFoundException {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
