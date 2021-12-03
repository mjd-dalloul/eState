package com.example.spring_tutorial.configuration.audit;

import com.example.spring_tutorial.domain.dto.auth_dto.CustomUserSecurity;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext :: getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication:: getPrincipal)
                .map(CustomUserSecurity.class::cast)
                .map(CustomUserSecurity::getUsername);
    }
}
