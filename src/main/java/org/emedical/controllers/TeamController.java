package org.emedical.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Team;
import org.emedical.models.requests.TeamRequest;
import org.emedical.service.TeamService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    @GetMapping("{id}")
    public ResponseEntity<String> getTeamName(@PathVariable Integer id) {
        Team team = teamService.findTeamById(id);
        return ResponseEntity.ok(team.getTeamName());
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Team> createTeam(@RequestBody TeamRequest request) {
        return ResponseEntity.ok(teamService.createTeam(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Team> updateTeam(@PathVariable Integer id,
                                           @Valid @RequestBody TeamRequest request) throws NotFoundException {
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteTeam(@PathVariable Integer id) throws NotFoundException {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
