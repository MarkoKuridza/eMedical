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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
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

    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.saveAdmin(admin);
    }

    @PutMapping("/edit/{id}")
    public Admin editUser(@PathVariable Integer id, @RequestBody Admin admin) {
        return adminService.editAdmin(admin);
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<String> activateAdmin(@PathVariable Integer id) {
        adminService.setActive(id);
        return ResponseEntity.ok("Admin with ID " + id + " activated successfully");
    }
    
    @PutMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateAdmin(@PathVariable Integer id) {
        adminService.setInactive(id);
        return ResponseEntity.ok("Admin with ID " + id + " deactivated successfully");
    }

    private boolean hasAccess(Integer id, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); //skida "Bearer "
        Claims claim = jwtService.extractAllClaims(token);
        Integer tokenAdminId = (Integer) claim.get("adminId");

        return id.equals(tokenAdminId);
    }
    
}
