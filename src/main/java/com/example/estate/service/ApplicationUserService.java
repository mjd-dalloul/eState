package com.example.estate.service;

import com.example.estate.domain.dto.auth_dto.CreateUserRequest;
import com.example.estate.domain.entity.ApplicationUser;
import com.example.estate.repository.ApplicationUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@RequiredArgsConstructor
public class ApplicationUserService {
    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ApplicationUser create(CreateUserRequest request) {
        if(userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ValidationException("Email Already in use");
        }

        ApplicationUser newUser = new ApplicationUser();
        newUser.setEmail(request.getEmail());
        newUser.setFullName(request.getFullName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(newUser);
        return newUser;
    }

}
