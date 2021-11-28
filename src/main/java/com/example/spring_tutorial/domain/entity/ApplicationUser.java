package com.example.spring_tutorial.domain.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

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
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class ApplicationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "Property")
//    private Set<Property> properties;
}
