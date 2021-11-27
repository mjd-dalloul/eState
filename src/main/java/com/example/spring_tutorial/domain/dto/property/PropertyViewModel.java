package com.example.spring_tutorial.domain.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyViewModel {

    @NotBlank
    private String description;
    private Integer shares;
    @NotNull
    @Min(1)
    private Integer price;

}
