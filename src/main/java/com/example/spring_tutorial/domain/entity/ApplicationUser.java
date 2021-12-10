package com.example.spring_tutorial.domain.entity;


import com.example.spring_tutorial.domain.dto.auth_dto.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"email"}
        )
})
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> authorities = new HashSet<>();

}
