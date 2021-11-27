package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.*;

@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"key"}
                )
        }
)
@Entity
@Getter @Setter @ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Constants {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String key;

    private Integer value;
}
