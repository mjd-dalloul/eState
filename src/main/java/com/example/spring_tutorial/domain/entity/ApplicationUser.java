package com.example.spring_tutorial.domain.entity;


import lombok.*;

import javax.persistence.*;

@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"email"}
        )
})
@Entity
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    private String password;
    private String fullName;
}
