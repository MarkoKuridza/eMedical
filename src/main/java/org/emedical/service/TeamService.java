package org.emedical.service;

import org.emedical.exceptions.NotFoundException;
import org.emedical.models.dto.Team;
import org.emedical.models.entities.TeamEntity;
import org.emedical.models.requests.TeamRequest;

import java.util.List;

public interface TeamService {
    Team findTeamById(Integer teamId) throws NotFoundException;

    List<Team> getAllTeams();

    Team createTeam(TeamRequest request);

    Team updateTeam(Integer id, TeamRequest request) throws NotFoundException;

    void deleteTeam(Integer id) throws NotFoundException;

    TeamEntity findExitingTeam(Integer id) throws NotFoundException;
}
