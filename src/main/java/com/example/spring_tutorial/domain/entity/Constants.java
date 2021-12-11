package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Constants {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(
            name = "parameter_key"
    )
    private String key;

    @Column(
            name = "parameter_value"
    )
    private Integer value;
}
