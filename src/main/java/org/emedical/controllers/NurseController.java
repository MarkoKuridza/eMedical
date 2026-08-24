package org.emedical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Appointment;
import org.emedical.models.dto.Nurse;
import org.emedical.models.dto.WaitingRoom;
import org.emedical.models.requests.NurseRequest;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.AppointmentService;
import org.emedical.service.NurseService;
import org.emedical.service.WaitingRoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nurses")
public class    NurseController {
    private final AppointmentService appointmentService;
    private final WaitingRoomService waitingRoomService;
    private final NurseService nurseService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Nurse>> getAllNurses() {
        return ResponseEntity.ok(nurseService.findAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<Nurse> registerNurse(@RequestBody NurseRequest request) {
        return ResponseEntity.ok(nurseService.create(request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}")
    public ResponseEntity<Nurse> updateNurse(@PathVariable Integer id,
                                             @Valid @RequestBody NurseRequest request) throws NotFoundException {
        return ResponseEntity.ok(nurseService.update(id, request));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNurse(@PathVariable Integer id) throws NotFoundException {
        nurseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('NURSE')")
    @GetMapping("/waiting-room")
    public ResponseEntity<List<WaitingRoom>> getWaitingRoom(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(waitingRoomService.getWaitingRoomByTeamId(user.getTeamId()));
    }

    @PreAuthorize("hasRole('NURSE')")
    @GetMapping("/pending-completion")
    public ResponseEntity<List<Appointment>> getPendingCompletion(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(appointmentService.getAppointmentsPendingCompletion(user.getTeamId()));
    }

    @PreAuthorize("hasRole('NURSE')")
    @GetMapping("/ready-for-completion")
    public ResponseEntity<List<WaitingRoom>> getReadyForCompletion(@AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(waitingRoomService.getReadyForNurse(user.getTeamId()));
    }

    @PreAuthorize("hasRole('NURSE')")
    @PutMapping("/appointments/{appointmentId}/arrival")
    public ResponseEntity<Appointment> notifyDoctorPatientArrived(@PathVariable Integer appointmentId,
                                                                  @AuthenticationPrincipal CustomUserDetails user) throws NotFoundException {
        return ResponseEntity.ok(appointmentService.markPatientArrived(appointmentId, user));
    }

    @PreAuthorize("hasRole('NURSE')")
    @PutMapping("/appointments/{appointmentId}/complete")
    public ResponseEntity<Appointment> completeAppointment(@PathVariable Integer appointmentId,
                                                           @AuthenticationPrincipal CustomUserDetails user) throws NotFoundException {
        return ResponseEntity.ok(appointmentService.completeAppointment(appointmentId, user));
    }

}
