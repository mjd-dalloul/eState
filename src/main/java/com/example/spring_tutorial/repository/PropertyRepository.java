package com.example.spring_tutorial.repository;

import com.example.spring_tutorial.domain.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    public List<Property> findBySaleInfoIsNull();
    @Modifying
    @Query(value = "UPDATE property" +
            "SET buyer_info_id = :buyerId, price = :price, sale_date= :date" +
            "WHERE id=:id", nativeQuery = true)
    public void updateSaleInfo(Long id, Integer price, Long buyerId, Date date);
}
