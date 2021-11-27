package com.example.spring_tutorial.domain.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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
    private Long id;

    private String key;

    private Integer value;
}
