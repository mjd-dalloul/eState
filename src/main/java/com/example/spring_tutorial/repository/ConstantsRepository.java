package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.configuration.AppConstant;
import com.example.spring_tutorial.domain.entity.Constants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstantsRepository extends JpaRepository<Constants, Long> {
    @Cacheable(value = AppConstant.appMainCacheName, key = "#key")
    public Constants findByKey(String key);
}
