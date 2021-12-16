package com.example.spring_tutorial.domain.dto.auth_dto;

import com.example.spring_tutorial.domain.entity.ApplicationUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorizedUserView {
    private ApplicationUser user;
    private String token;
}