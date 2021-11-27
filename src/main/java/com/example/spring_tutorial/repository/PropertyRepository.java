package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {

}
