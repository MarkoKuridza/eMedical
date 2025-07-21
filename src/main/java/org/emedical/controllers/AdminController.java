package org.emedical.controllers;

import java.util.List;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Admin;
import org.emedical.service.JwtService;
import org.emedical.service.impl.AdminServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admins")
public class AdminController {
    
    private final JwtService jwtService;
    private final AdminServiceImpl adminService;

    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable int id) {
        try{
            Admin admin = adminService.findById(id);
            return ResponseEntity.ok(admin);
        }catch(NotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    private boolean hasAccess(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); //skida "Bearer "
        Claims claim = jwtService.extractAllClaims(token);
        Integer tokenAdminId = (Integer) claim.get("adminId");

        return id.equals(tokenAdminId);
    }
    
}
