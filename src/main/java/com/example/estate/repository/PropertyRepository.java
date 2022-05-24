package com.example.estate.repository;

import com.example.estate.domain.entity.Property;
import com.example.estate.domain.entity.SaleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    public List<Property> findBySaleInfoIsNull();

    @Transactional @Modifying
    @Query("update Property p set p.saleInfo = :saleInfo where p.id = :id")
    public void updateSaleInfo(
            @Param("id") Long id,
            @Param("saleInfo") SaleInfo saleInfo
    );
}
