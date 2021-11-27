package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    public List<Property> findBySaleInfoIsNull();
}
