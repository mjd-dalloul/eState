package com.example.spring_tutorial.domain.dto.auth_dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class CreateUserRequest {
    @NotBlank @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;

}
