package com.example.estate.repository;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.entity.Constants;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConstantsRepository extends JpaRepository<Constants, Long> {
    @Cacheable(value = AppConstant.appMainCacheName, key = "#key")
    public Constants findByKey(String key);
}
