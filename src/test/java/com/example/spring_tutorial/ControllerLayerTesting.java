package com.example.spring_tutorial;

import com.example.spring_tutorial.api.PropertyApi;
import com.example.spring_tutorial.domain.dto.property.NewProperty;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.service.PropertyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
public class ControllerLayerTesting {
    @Mock
    PropertyService propertyService;

    @InjectMocks
    PropertyApi api;

    @Autowired
    MockMvc mockMvc;

    Property property;

    @BeforeEach
    public void init() {
        property = Property.builder()
                .id(1L)
                .description("test")
                .price(213L)
                .shares(6)
                .build();
        mockMvc = MockMvcBuilders.standaloneSetup(api).build();
    }

    @Test
    public void addProperty() throws Exception {
        when(propertyService.addProperty(any())).thenReturn(property);
        NewProperty newProperty = NewProperty.builder()
                .shares(property.getShares())
                .price(property.getPrice())
                .description(property.getDescription())
                .build();

        mockMvc.perform(post("/api/property")
                .contentType(asJsonString(newProperty))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));
        verify(propertyService, times(1)).addProperty(any());

    }

    private String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
