package com.example.estate.domain.dto.constants_dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConstantViewModel {
    @NotNull
    private Long id;
    @NotBlank
    private String key;
    @Min(1)
    @NotNull
    private Integer value;
}
