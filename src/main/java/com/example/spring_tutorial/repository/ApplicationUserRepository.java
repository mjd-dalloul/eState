package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.entity.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findByEmail(String email);
}

