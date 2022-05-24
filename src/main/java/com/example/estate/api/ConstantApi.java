package com.example.estate.api;

import com.example.estate.domain.dto.BaseResponse;
import com.example.estate.domain.dto.constants_dto.ConstantViewModel;
import com.example.estate.domain.entity.Constants;
import com.example.estate.service.ConstantService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "api/constant")
@RequiredArgsConstructor
@Profile("!receiver")
public class ConstantApi {
    private final ConstantService service;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("")
    public ResponseEntity<BaseResponse<Constants>> changeConstantValue(
            @RequestBody @Valid ConstantViewModel constantViewModel
    ) {
        return ResponseEntity.ok().body(
                BaseResponse.<Constants>builder()
                        .success(true)
                        .data(service.setNewValueForConstant(constantViewModel))
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<BaseResponse<List<Constants>>> fetchConstants() {
        return ResponseEntity.ok().body(
                BaseResponse.<List<Constants>>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .data(service.fetchConstants())
                        .build()
        );
    }
}
