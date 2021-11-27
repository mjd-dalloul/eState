package com.example.spring_tutorial.service;

import com.example.spring_tutorial.domain.dto.property.BuyerInfo;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.domain.entity.SaleInfo;
import com.example.spring_tutorial.repository.ApplicationUserRepository;
import com.example.spring_tutorial.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class BuyingService {
    private final PropertyRepository repository;
    private final ApplicationUserRepository userRepository;

    public Property buyProperty(BuyerInfo info, Long propertyId) {
        Property property = repository.getById(propertyId);
        SaleInfo saleInfo = SaleInfo.builder().salePrice(info.getPrice())
                .buyerInfo(userRepository.getById(info.getId()))
                .saleDate(new Date(System.currentTimeMillis()))
                .build();
        property.setSaleInfo(saleInfo);
        repository.save(property);
        return property;
    }
}
