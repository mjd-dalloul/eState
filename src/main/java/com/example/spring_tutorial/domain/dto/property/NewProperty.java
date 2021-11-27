package com.example.spring_tutorial.domain.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewProperty {
    @NotBlank
    @Min(1)
    private Integer price;
    @NotBlank
    private String description;
    private Integer shares;
}
