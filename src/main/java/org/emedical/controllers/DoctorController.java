package org.emedical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.Doctor;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.requests.DoctorRequest;
import org.emedical.models.responses.DoctorResponse;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final WaitingRoomService waitingRoomService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Doctor> registerDoctor(@RequestBody DoctorRequest request) {
        return ResponseEntity.ok(doctorService.registerDoctor(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer id,
                                               @Valid @RequestBody DoctorRequest request) throws NotFoundException {
        return ResponseEntity.ok(doctorService.updateDoctor(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) throws NotFoundException {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/queue")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<WaitingRoom>> getDoctorQueue(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(waitingRoomService.getDoctorQueue(user.getId()));
    }

    @GetMapping("/{doctorId}/appointments")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<Appointment>> getDoctorAppointments(@PathVariable Integer doctorId,
                                                                   @AuthenticationPrincipal CustomUserDetails user) {
        if (!doctorId.equals(user.getId())) {
            throw new BadRequestException("You can only view your own appointments.");
        }
        return ResponseEntity.ok(doctorService.getDoctorAppointments(doctorId));
    }

    @PutMapping("/appointments/{id}/start")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Appointment> startAppointment(@PathVariable Integer id,
                                                        @AuthenticationPrincipal CustomUserDetails user) throws NotFoundException {
        return ResponseEntity.ok(doctorService.startAppointment(id, user));
    }

    @GetMapping("/team/{id}")
    @PreAuthorize("hasRole('NURSE')")
    public ResponseEntity<DoctorResponse> getDoctorByTeamId(@PathVariable Integer id,
                                                    @AuthenticationPrincipal CustomUserDetails user) throws NotFoundException {
        return ResponseEntity.ok(doctorService.getDoctorByTeamId(id));
    }
}
