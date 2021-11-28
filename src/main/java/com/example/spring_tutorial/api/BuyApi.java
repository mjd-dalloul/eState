package com.example.spring_tutorial.api;

import com.example.spring_tutorial.domain.dto.BaseResponse;
import com.example.spring_tutorial.domain.dto.property.BuyerInfo;
import com.example.spring_tutorial.domain.entity.Property;
import com.example.spring_tutorial.service.BuyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/buy")
@RequiredArgsConstructor
public class BuyApi {
    private final BuyingService buyingService;

    @PutMapping("")
    public ResponseEntity<BaseResponse<Property>> buyProperty(@RequestParam Long id, @RequestBody @Valid BuyerInfo buyerInfo) {
        buyingService.buyProperty(buyerInfo, id);
        return ResponseEntity.ok().body(
                BaseResponse.<Property>builder()
                        .statusCode(HttpStatus.OK.value())
                        .success(true)
                        .build()
        );
    }

}