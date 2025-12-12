package org.emedical.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;
import org.emedical.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
        public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request, HttpServletResponse response) throws NotFoundException {
        LoginResponse info = authService.login(request, response);

        Map<String, Object> body = new HashMap<>();
        body.put("role", info.getRole());

        return ResponseEntity.ok(body);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        authService.logout(response);
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/check")
    public ResponseEntity<Map<String, Object>> checkAuth(HttpServletRequest request) {
        boolean isAuthenticated = authService.checkAuth(request);
        if(!isAuthenticated){
            return ResponseEntity.ok(
                    Map.of("authenticated", false)
            );
        }

        String role = authService.checkRole(request);
        return ResponseEntity.ok(Map.of("authenticated", true,
                                        "role", role));
    }
}
