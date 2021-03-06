package com.example.estate.service;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.property.NewProperty;
import com.example.estate.domain.dto.property.PropertyViewModel;
import com.example.estate.domain.entity.Property;
import com.example.estate.domain.exception.NotFoundException;
import com.example.estate.repository.ApplicationUserRepository;
import com.example.estate.repository.ConstantsRepository;
import com.example.estate.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {
    private final PropertyRepository repository;
    private final ConstantsRepository constantsRepository;
    private final ApplicationUserRepository userRepository;

    public void deleteProperty(Long id) {
        final Property property = repository.findById(id).orElseThrow(
                () -> new NotFoundException("Property not found with the given ID")
        );
        repository.delete(property);
    }

    public Property updateProperty(Long id, PropertyViewModel property) {
        Property p = repository.findById(id).orElseThrow();
        if (property.getPrice() != null) {
            p.setPrice(property.getPrice());
        }
        if (property.getDescription() != null) {
            p.setDescription(property.getDescription());
        }
        if (property.getShares() != null) {
            p.setShares(property.getShares());
        }
        repository.save(p);
        return p;
    }

    public Property addProperty(NewProperty newProperty) {
        Property property = Property.builder()
                .description(newProperty.getDescription())
                .price(newProperty.getPrice())
                .shares(newProperty.getShares())
                .build();
        if (property.getShares() == null) {
            property.setShares(constantsRepository.findByKey(AppConstant.share).getValue());
        }
        repository.save(property);
        return property;
    }


    public List<Property> fetchAvailableProperty() {
        return repository.findBySaleInfoIsNull();
    }

    public List<Property> fetchAllProperty() {
        return repository.findAll();
    }

    public Property fetchPropertyById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NotFoundException("Property not found with the given ID")
        );
    }
}