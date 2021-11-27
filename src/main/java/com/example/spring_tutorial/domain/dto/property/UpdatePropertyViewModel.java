package com.example.spring_tutorial.domain.dto.property;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePropertyViewModel {

    private String description;
    private Integer shares;
    private Integer price;

}
