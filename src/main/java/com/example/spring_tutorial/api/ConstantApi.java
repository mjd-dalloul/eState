package com.example.spring_tutorial.api;

import com.example.spring_tutorial.domain.dto.BaseResponse;
import com.example.spring_tutorial.domain.dto.constants_dto.ConstantViewModel;
import com.example.spring_tutorial.service.ConstantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/constant")
@RequiredArgsConstructor
public class ConstantApi {
    private final ConstantService service;

    @PutMapping("")
    public ResponseEntity<BaseResponse<Void>> changeConstantValue(
            @RequestBody @Valid ConstantViewModel constantViewModel
    ) {
        service.setNewValueForConstant(constantViewModel);
        return ResponseEntity.ok().body(
                BaseResponse.<Void>builder()
                        .success(true)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }
}
