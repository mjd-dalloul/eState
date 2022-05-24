package com.example.estate.domain.dto.auth_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserView {
    private Long id;
    private String email;
    private String fullName;
}
