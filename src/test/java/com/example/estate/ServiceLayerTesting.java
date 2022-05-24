package com.example.estate;

import com.example.estate.configuration.AppConstant;
import com.example.estate.domain.dto.property.NewProperty;
import com.example.estate.domain.dto.property.PropertyViewModel;
import com.example.estate.domain.entity.Constants;
import com.example.estate.domain.entity.Property;
import com.example.estate.repository.ConstantsRepository;
import com.example.estate.repository.PropertyRepository;
import com.example.estate.service.PropertyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ServiceLayerTesting {
    @Mock
    PropertyRepository repository;
    @Mock
    ConstantsRepository constantsRepository;



    @Autowired
    @InjectMocks
    PropertyService propertyService;

    Property property;
    Constants constants;

    @BeforeEach
    public void init() {
        property = Property.builder()
                .id(1L)
                .description("test")
                .price(213L)
                .build();
        constants = Constants.builder()
                .key(AppConstant.share)
                .value(7)
                .build();
    }

    @Test
    void addProperty() {
        when(repository.save(any())).thenReturn(property);
        when(constantsRepository.findByKey(any())).thenReturn(constants);
        propertyService.addProperty(NewProperty.builder()
                        .description(property.getDescription())
                        .price(property.getPrice())
                .build());
        verify(repository, times(1)).save(any());
        verify(constantsRepository, times(1)).findByKey(AppConstant.share);
    }

    @Test
    void updateProperty() {
        when(repository.findById(any())).thenReturn(java.util.Optional.ofNullable(property));
        when(repository.save(any())).thenReturn(property);
        propertyService.updateProperty(property.getId(), PropertyViewModel.builder()
                        .description(property.getDescription())
                        .price(property.getPrice())
                        .shares(property.getShares())
                .build());
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).save(notNull());
    }

    @Test
    void deleteProperty() {
        when(repository.findById(any())).thenReturn(java.util.Optional.ofNullable(property));
        propertyService.deleteProperty(property.getId());
        verify(repository, times(1)).findById(property.getId());
    }
}
