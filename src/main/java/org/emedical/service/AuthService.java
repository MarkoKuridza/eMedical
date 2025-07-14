package org.emedical.service;

import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
}
