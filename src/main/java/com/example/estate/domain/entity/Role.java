package com.example.estate.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Role implements GrantedAuthority {
    public static final String USER_ADMIN = "ROLE_ADMIN";
    @Id
    private String authority;


}
