package org.emedical.controllers;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.requests.AppointmentRequest;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AppointmentService;
import org.emedical.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping("/appointments")
    public ResponseEntity<List<Appointment>> getAllAppointmentsByTeamId(@AuthenticationPrincipal CustomUserDetails user) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByTeamId(user.getTeamId());
        return ResponseEntity.ok(appointments);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addAppointmentForDoctor(@RequestBody AppointmentRequest request,
                                                     @AuthenticationPrincipal CustomUserDetails user){
        var response = appointmentService.createAppointment(request, user);
        return ResponseEntity.ok(response);
    }


    //ako bi radio audit i logovanje dodati @AuthenticationPrincipal i polje updatedBy
    @PreAuthorize("hasRole('NURSE')")
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> updateAppointment(@PathVariable Integer id,
                                               @RequestBody Appointment updatedAppointment) throws NotFoundException {
        var response = appointmentService.updateAppointment(id, updatedAppointment);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('NURSE')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Integer id) throws NotFoundException {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }

}