package com.example.spring_tutorial.api;

import com.example.spring_tutorial.domain.dto.BaseResponse;
import com.example.spring_tutorial.domain.dto.property.NewProperty;
import com.example.spring_tutorial.domain.dto.property.PropertyViewModel;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/property")
@RequiredArgsConstructor
public class PropertyApi {

    private final PropertyService service;

    @GetMapping("")
    public ResponseEntity<BaseResponse<List<Property>>> getAllProperty() {
        final List<Property> properties = service.fetchAllProperty();
        return ResponseEntity.ok().body(
                BaseResponse.<List<Property>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .data(properties)
                        .build()
        );
    }

    @DeleteMapping("")
    public ResponseEntity<BaseResponse<Void>> deleteProperty(@RequestParam Long id) {
        service.deleteProperty(id);
        return ResponseEntity.ok().body(
                BaseResponse.<Void>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .build()
        );
    }

    @PutMapping("")
    public ResponseEntity<BaseResponse<Property>> updateProperty(
            @RequestParam Long id, @RequestBody @Valid PropertyViewModel propertyViewModel) {
        final Property updatedProperty = service.updateProperty(id, propertyViewModel);
        return ResponseEntity.ok().body(
                BaseResponse.<Property>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(updatedProperty)
                        .build()
        );
    }

    @PostMapping("")
    public ResponseEntity<BaseResponse<Property>> createProperty(
            @RequestBody @Valid NewProperty newProperty) {
        final Property property = service.addProperty(newProperty);
        return ResponseEntity.ok().body(
                BaseResponse.<Property>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .success(true)
                        .data(property)
                        .build()
        );
    }

    @GetMapping("/availableProperty")
    public ResponseEntity<BaseResponse<List<Property>>> getAvailableProperty() {
        final List<Property> properties = service.fetchAvailableProperty();
        return ResponseEntity.ok().body(
                BaseResponse.<List<Property>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .data(properties)
                        .build()
        );
    }
}
