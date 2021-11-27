package com.example.spring_tutorial.service;

import com.example.spring_tutorial.configuration.AppConstant;
import com.example.spring_tutorial.domain.dto.property.BuyerInfo;
import com.example.spring_tutorial.domain.dto.property.NewProperty;
import com.example.spring_tutorial.domain.dto.property.UpdatePropertyViewModel;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.domain.entity.SaleInfo;
import com.example.spring_tutorial.repository.ApplicationUserRepository;
import com.example.spring_tutorial.repository.ConstantsRepository;
import com.example.spring_tutorial.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository repository;
    private final ConstantsRepository constantsRepository;
    private final ApplicationUserRepository userRepository;

    public void deleteProperty(Long id) {
        final Property property = repository.getById(id);
        repository.delete(property);
    }

    public void updateProperty(Long id, UpdatePropertyViewModel property) {
        Property p = repository.getById(id);
        if(property.getPrice() != null) {
            p.setPrice(property.getPrice());
        }
        if(property.getDescription() != null) {
            p.setDescription(property.getDescription());
        }
        if(property.getShares() != null) {
            p.setShares(property.getShares());
        }
        repository.save(p);
    }

    public void addProperty(NewProperty newProperty) {
        Property property = Property.builder()
                .description(newProperty.getDescription())
                .price(newProperty.getPrice())
                .shares(newProperty.getShares())
                .build();
        if(property.getShares() == null) {
            property.setShares(constantsRepository.findByKey(AppConstant.share).getValue());
        }
        repository.save(property);
    }

    public void buyProperty(BuyerInfo info, Long propertyId) {
        Property property = repository.getById(propertyId);
        SaleInfo saleInfo = SaleInfo.builder().salePrice(info.getPrice())
                .buyerInfo(userRepository.getById(info.getId()))
                .saleDate(new Date(System.currentTimeMillis()))
                .build();
        property.setSaleInfo(saleInfo);
        repository.save(property);
    }

}