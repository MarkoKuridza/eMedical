package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.entities.UserEntity;
import org.emedical.models.enums.Role;
import org.emedical.repositories.DoctorEntityRepository;
import org.emedical.repositories.NurseEntityRepository;
import org.emedical.repositories.UserEntityRepository;
import org.emedical.security.CustomUserDetails;
import org.emedical.service.CustomUserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserEntityRepository repository;
    private final DoctorEntityRepository doctorEntityRepository;
    private final NurseEntityRepository nurseEntityRepository;

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Integer teamId = null;

        if(entity.getRole() == Role.DOCTOR) {
            teamId = doctorEntityRepository.findById(entity.getId())
                    .map(d -> d.getTeam().getTeamId())
                    .orElse(null);
        }

        if(entity.getRole() == Role.NURSE) {
            teamId = nurseEntityRepository.findById(entity.getId())
                    .map(n -> n.getTeam().getTeamId())
                    .orElse(null);
        }

        return new CustomUserDetails(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole(),
                teamId
        );
    }
}
