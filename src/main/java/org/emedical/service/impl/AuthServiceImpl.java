package org.emedical.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.LoginRequest;
import org.emedical.models.dto.LoginResponse;
import org.emedical.models.entities.DoctorEntity;
import org.emedical.models.entities.NurseEntity;
import org.emedical.models.entities.UserEntity;
import org.emedical.models.enums.Role;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.repositories.UserEntityRepository;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AuthService;
import org.emedical.service.JwtService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("role", user.getRole());
        claims.put("teamId", user.getTeamId());

        String token = jwtService.generateToken(claims, user);

        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)
                .secure(false) //lokalno se koristi http, ako postavim na true ide preko https protokola
                .sameSite("Lax")
                .path("/")
                .build();

        response.addHeader("Set-Cookie", cookie.toString());

        return new LoginResponse(user.getRole().toString());
    }

    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("jwt", "")
                .httpOnly(true)
                .secure(false) //lokalno se koristi http, ako postavim na true ide preko https protokola
                .sameSite("Lax")
                .path("/")
                .maxAge(0)
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }


    public boolean canAccessTeamId(Integer id) {
        Claims claims = getClaims();
        Integer teamId = (Integer) claims.get("teamId");
        return teamId.equals(id);
    }

    public String checkRole(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null)
            for(Cookie cookie : cookies) {
                if("jwt".equals(cookie.getName())){
                    String token = cookie.getValue();
                    Claims claims = jwtService.extractAllClaims(token);
                    return claims.get("role", String.class);
                }
            }
        return "";
    }

    public boolean checkAuth(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if(cookies != null)
            for(Cookie cookie : cookies) {
                if("jwt".equals(cookie.getName())){
                    String token = cookie.getValue();
                    return jwtService.validateToken(token);
                }
        }
        return false;
    }

    private Claims getClaims() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || auth.getCredentials() == null) {
            throw new IllegalStateException("There is no JWT token");
        }

        String token = auth.getCredentials().toString();
        return jwtService.extractAllClaims(token);
    }
}
