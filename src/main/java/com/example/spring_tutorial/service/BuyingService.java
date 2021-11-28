package com.example.spring_tutorial.service;

import com.example.spring_tutorial.domain.dto.property.BuyerInfo;
import com.example.spring_tutorial.domain.entity.ApplicationUser;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.domain.entity.SaleInfo;
import com.example.spring_tutorial.domain.exception.NotFoundException;
import com.example.spring_tutorial.repository.ApplicationUserRepository;
import com.example.spring_tutorial.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
@RequiredArgsConstructor
public class BuyingService {
    private final PropertyRepository repository;

    //todo optimize with one query
    public void buyProperty(BuyerInfo info, Long propertyId) {
//        repository.updateSaleInfo(
//                propertyId,
//                SaleInfo.builder()
//                        .buyerInfo(
//                                ApplicationUser.builder()
//                                .id(info.getId())
//                                .build()
//                        )
//                        .saleDate(new Date(System.currentTimeMillis()))
//                        .salePrice(info.getPrice())
//                        .build()
//        );
        final Property p = repository.findById(propertyId).orElseThrow(
                () -> new NotFoundException("Can not found property with this id")
        );
        p.setSaleInfo(SaleInfo.builder()
                .salePrice(info.getPrice())
                .buyerInfo(
                        ApplicationUser.builder()
                        .id(info.getId())
                        .build()
                )
                .saleDate(new Date(System.currentTimeMillis()))
                .build()
        );
        repository.save(p);
    }
}
