package com.example.estate.domain.dto.auth_dto;

import com.example.estate.domain.entity.ApplicationUser;
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