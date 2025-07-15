package org.emedical.service.impl;

import lombok.RequiredArgsConstructor;
import org.emedical.models.dto.User;
import org.emedical.models.entities.UserEntity;
import org.emedical.repositories.UserEntityRepository;
import org.emedical.service.CustomUserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    private final UserEntityRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        User user = modelMapper.map(entity, User.class);

        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
