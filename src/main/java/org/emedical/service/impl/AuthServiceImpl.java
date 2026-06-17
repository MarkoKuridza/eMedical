package org.emedical.service.impl;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.emedical.models.requests.LoginRequest;
import org.emedical.models.responses.LoginResponse;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AuthService;
import org.emedical.service.JwtService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
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

    @Override
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

    @Override
    public Map<String, Object> getAuthInfo(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("jwt".equals(cookie.getName())) {
                    String token = cookie.getValue();

                    if (!jwtService.validateToken(token)) break;
                    Claims claims = jwtService.extractAllClaims(token);

                    Map<String, Object> info = new HashMap<>();
                    info.put("authenticated", true);
                    info.put("role", claims.get("role").toString());
                    info.put("id", claims.get("userId"));
                    info.put("teamId", claims.get("teamId"));
                    return info;
                }
            }
        }
        return Map.of("authenticated", false);
    }

//    private Claims getClaims() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        if (auth == null || auth.getCredentials() == null) {
//            throw new IllegalStateException("There is no JWT token");
//        }
//
//        String token = auth.getCredentials().toString();
//        return jwtService.extractAllClaims(token);
//    }
}
