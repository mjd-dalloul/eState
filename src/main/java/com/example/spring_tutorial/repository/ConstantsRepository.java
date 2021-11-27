package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.entity.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConstantsRepository extends JpaRepository<Constants, Long> {
    public Constants findByKey(String key);
}
