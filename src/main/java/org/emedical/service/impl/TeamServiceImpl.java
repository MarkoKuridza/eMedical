package org.emedical.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.emedical.exceptions.BadRequestException;
import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Team;
import org.emedical.models.entities.TeamEntity;
import org.emedical.models.requests.TeamRequest;
import org.emedical.repositories.TeamEntityRepository;
import org.emedical.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamEntityRepository teamRepository;

    private final ModelMapper modelMapper;

    @Override
    public Team findTeamById(Integer teamId) throws NotFoundException {
        return modelMapper.map(teamRepository.findTeamEntityByTeamId(teamId)
                .orElseThrow(() -> new NotFoundException("Team not found")), Team.class);
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map((t) -> modelMapper.map(t, Team.class))
                .collect(Collectors.toList());
    }

    @Override
    public Team createTeam(TeamRequest request) {
        if (teamRepository.existsTeamEntityByTeamName(request.getTeamName())) {
            throw new BadRequestException("Name already taken!");
        }
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setTeamName(request.getTeamName());

        teamRepository.save(teamEntity);
        return modelMapper.map(teamEntity, Team.class);
    }

    @Override
    public Team updateTeam(Integer id, TeamRequest request) throws NotFoundException {
        TeamEntity teamEntity = findExitingTeam(id);

        teamEntity.setTeamName(request.getTeamName());

        teamRepository.save(teamEntity);
        return modelMapper.map(teamEntity, Team.class);
    }

    @Override
    public void deleteTeam(Integer id) throws NotFoundException {
        TeamEntity teamEntity = findExitingTeam(id);

        if (teamEntity.getDoctor() != null
                || (teamEntity.getNurses() != null && !teamEntity.getNurses().isEmpty())
                || (teamEntity.getPatients() != null && !teamEntity.getPatients().isEmpty())
                || (teamEntity.getAppointments() != null && !teamEntity.getAppointments().isEmpty())) {
            throw new BadRequestException("Team cannot be deleted while it has assigned doctors, nurses, patients, or appointments.");
        }
        teamRepository.delete(teamEntity);
    }

    @Override
    public TeamEntity findExitingTeam(Integer id) throws NotFoundException {
        return teamRepository.findTeamEntityByTeamId(id)
                .orElseThrow(() -> new NotFoundException("Team not found"));
    }
}
