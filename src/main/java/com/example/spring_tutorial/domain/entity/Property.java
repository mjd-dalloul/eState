package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter @Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String description;

    private Integer shares;

    private Integer price;

    @Embedded
    private SaleInfo saleInfo;

}