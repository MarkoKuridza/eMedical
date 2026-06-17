package org.emedical.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.requests.LoginRequest;
import org.emedical.models.responses.LoginResponse;

import java.util.Map;

public interface AuthService {
    LoginResponse login(LoginRequest request, HttpServletResponse response) throws NotFoundException;

    void logout(HttpServletResponse response);

    Map<String, Object> getAuthInfo(HttpServletRequest request);
}
