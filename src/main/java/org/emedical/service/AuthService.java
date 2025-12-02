package org.emedical.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request, HttpServletResponse response) throws NotFoundException;
    void logout(HttpServletResponse response);
    boolean canAccessTeamId(Integer id);

    boolean checkAuth(HttpServletRequest request);
}
