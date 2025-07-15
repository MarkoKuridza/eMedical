package org.emedical.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
    boolean isTokenValid(String token, UserDetails userDetails);
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);
    Claims extractAllClaims(String token);
}
