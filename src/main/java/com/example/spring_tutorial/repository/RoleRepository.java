package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.dto.auth_dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
