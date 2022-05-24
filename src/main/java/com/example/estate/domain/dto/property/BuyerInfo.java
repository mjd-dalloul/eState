package com.example.estate.domain.dto.property;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class BuyerInfo {
    @NotNull
    private Long id;
    @NotNull
    @Min(1)
    private Long price;
}
