package com.example.estate.api;

import com.example.estate.domain.dto.BaseResponse;
import com.example.estate.domain.dto.property.NewProperty;
import com.example.estate.domain.dto.property.PropertyViewModel;
import com.example.estate.domain.entity.Property;
import com.example.estate.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/property")
@RequiredArgsConstructor
@Profile("!receiver")
public class PropertyApi {

    private final PropertyService service;

    @GetMapping("")
    public ResponseEntity<?> getPropertyHandler(@RequestParam @Nullable Long id) {
        if(id == null) {
            return getAllProperty();
        } else {
           return getPropertyById(id);
        }
    }

    private ResponseEntity<BaseResponse<List<Property>>> getAllProperty() {
        final List<Property> properties = service.fetchAllProperty();
        return ResponseEntity.ok().body(
                BaseResponse.<List<Property>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .data(properties)
                        .build()
        );
    }

    private ResponseEntity<BaseResponse<Property>> getPropertyById(Long id) {
        final  Property property = service.fetchPropertyById(id);
        return ResponseEntity.ok().body(
                BaseResponse.<Property>builder()
                        .data(property)
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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
