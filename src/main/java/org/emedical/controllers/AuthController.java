package org.emedical.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.requests.LoginRequest;
import org.emedical.models.responses.LoginResponse;
import org.emedical.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request, HttpServletResponse response) throws NotFoundException {
        LoginResponse info = authService.login(request, response);

        return ResponseEntity.ok(Map.of("role", info.getRole()));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAuth(HttpServletRequest request) {
        return ResponseEntity.ok(authService.getAuthInfo(request));
    }
}
